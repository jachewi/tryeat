package shop.tryit.web.question;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFormDto {

    private Long questionId;

    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;
   
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

}
