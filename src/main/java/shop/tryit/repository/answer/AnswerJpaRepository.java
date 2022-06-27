package shop.tryit.repository.answer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.question.Question;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestion(Question question);

}
