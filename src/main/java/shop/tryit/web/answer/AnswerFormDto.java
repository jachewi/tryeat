package shop.tryit.web.answer;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnswerFormDto {

    private Long id;

    @NotBlank(message = "댓글 입력이 필요합니다.")
    private String content;

    private LocalDateTime createdDateTime;
    
}