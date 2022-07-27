package shop.tryit.repository.answer;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.question.entity.Question;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestion(Question question);

    Page<Answer> findSearchByQuestion(Question question, Pageable pageable);

}
