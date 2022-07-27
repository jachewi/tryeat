package shop.tryit.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.question.entity.Question;

public interface QuestionJpaRepository extends JpaRepository<Question, Long>, QuestionCustom {

}
