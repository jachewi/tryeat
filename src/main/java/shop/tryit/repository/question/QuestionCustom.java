package shop.tryit.repository.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.tryit.domain.question.QuestionSearchCondition;
import shop.tryit.domain.question.QuestionSearchDto;

public interface QuestionCustom {

    Page<QuestionSearchDto> searchQuestion(QuestionSearchCondition condition, Pageable pageable);

}
