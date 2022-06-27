package shop.tryit.domain.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.answer.AnswerRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public Long register(Question question) {
        return questionRepository.save(question);
    }

    public Question findOne(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 질문을 찾을 수 없습니다."));
    }

    @Transactional
    public Long update(Long id, Question newQuestion) {
        Question findQuestion = findOne(id);
        findQuestion.update(newQuestion.getTitle(), newQuestion.getContent());
        return findQuestion.getId();
    }

    @Transactional
    public void delete(Long id) {
        Question question = findOne(id);
        questionRepository.delete(question);
        answerRepository.findByQuestion(question)
                .forEach(answerRepository::delete);
    }

}
