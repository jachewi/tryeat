package shop.tryit.domain.question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionSearchCondition {

    private String title;
    private String email;

    @Builder
    private QuestionSearchCondition(String title, String email) {
        this.title = title;
        this.email = email;
    }

}
