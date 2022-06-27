package shop.tryit.domain.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

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

}
