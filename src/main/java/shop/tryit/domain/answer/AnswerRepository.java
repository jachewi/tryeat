package shop.tryit.domain.answer;

import java.util.List;
import java.util.Optional;
import shop.tryit.domain.question.Question;

public interface AnswerRepository {

    Long save(Answer answer);

    Optional<Answer> findById(Long id);

    List<Answer> findByQuestion(Question question);

    void delete(Answer answer);

}
