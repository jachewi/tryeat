package shop.tryit.repository.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.qna.entity.Question;

public interface QuestionJpaRepository extends JpaRepository<Question, Long>, QuestionCustom {

}
