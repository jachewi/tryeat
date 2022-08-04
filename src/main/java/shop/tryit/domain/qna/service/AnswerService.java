package shop.tryit.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import shop.tryit.domain.qna.entity.Answer;
import shop.tryit.domain.qna.entity.Question;
import shop.tryit.domain.qna.repository.AnswerRepository;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    public Long register(Answer answer) {
        return answerRepository.save(answer);
    }

    public Long update(Long id, Answer newAnswer) {
        Answer findAnswer = findOne(id);
        findAnswer.update(newAnswer.getContent());
        return findAnswer.getId();
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    private Answer findOne(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다."));
    }

    public Page<Answer> findAnswersByQuestionId(Long questionId, int pageNumber) {
        Question question = questionService.findOne(questionId);
        Sort sortBy = Sort.by(Direction.DESC, "createDate");
        PageRequest pageRequest = PageRequest.of(pageNumber, 3, sortBy);
        return answerRepository.findSearchByQuestion(question, pageRequest);
    }

    public Answer findById(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalStateException("해당 답변이 없습니다."));
    }

}
