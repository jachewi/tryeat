package shop.tryit.domain.question.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuestionSearchDto {

    private long questionId;
    private String title;
    private long numberOfAnswer;
    private String email;
    private LocalDateTime createDate;

    @QueryProjection
    public QuestionSearchDto(long questionId, String title, String email, LocalDateTime createDate) {
        this.questionId = questionId;
        this.title = title;
        this.email = email;
        this.createDate = createDate;
    }

    public void designateNumberOfAnswer(long numberOfAnswer) {
        this.numberOfAnswer = numberOfAnswer;
    }

}
