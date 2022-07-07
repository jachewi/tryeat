package shop.tryit.web.answer;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerFormDto {

    @NotEmpty(message = "댓글 입력이 필요합니다.")
    private String content;

}