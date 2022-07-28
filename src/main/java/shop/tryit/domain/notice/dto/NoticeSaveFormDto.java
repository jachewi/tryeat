package shop.tryit.domain.notice.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeSaveFormDto {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

}
