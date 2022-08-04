package shop.tryit.domain.qna.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class QuestionCheckPasswordFormDto {

    @Length(min = 4, max = 4, message = "비밀번호는 4자리입니다.")
    private String password;

}
