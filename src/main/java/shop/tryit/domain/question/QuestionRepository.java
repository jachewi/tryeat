package shop.tryit.domain.question;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.tryit.domain.question.dto.QuestionSearchCondition;
import shop.tryit.domain.question.dto.QuestionSearchDto;
import shop.tryit.domain.question.entity.Question;

public interface QuestionRepository {

    Long save(Question question);

    Optional<Question> findById(Long id);

    void delete(Question question);

    Page<QuestionSearchDto> searchQuestion(QuestionSearchCondition condition, Pageable pageable);

}
