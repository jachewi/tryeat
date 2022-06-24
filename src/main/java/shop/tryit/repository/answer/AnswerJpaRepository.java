package shop.tryit.repository.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.answer.Answer;

public interface AnswerJpaRepository extends JpaRepository<Answer, Long> {
}
