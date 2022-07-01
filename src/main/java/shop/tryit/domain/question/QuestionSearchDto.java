package shop.tryit.domain.question;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionSearchDto {

    private long QuestionId;
    private String title;
    private long numberOfAnswer;
    private String email;

    @QueryProjection
    public QuestionSearchDto(long questionId, String title, String email) {
        QuestionId = questionId;
        this.title = title;
        this.email = email;
    }

    public void designateNumberOfAnswer(long numberOfAnswer) {
        this.numberOfAnswer = numberOfAnswer;
    }

}
