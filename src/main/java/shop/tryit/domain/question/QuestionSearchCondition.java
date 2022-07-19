package shop.tryit.domain.question;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionSearchCondition {

    private String condition;

    @Builder
    private QuestionSearchCondition(String condition, String email) {
        this.condition = condition;
    }

}
