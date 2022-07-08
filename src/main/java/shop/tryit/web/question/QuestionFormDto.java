package shop.tryit.web.question;

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
    private String title;
    private String content;

}
