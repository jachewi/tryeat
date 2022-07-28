package shop.tryit.domain.notice.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeUpdateFormDto {

    private Long id;

    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

    private String memberEmail;

    @Builder
    private NoticeUpdateFormDto(Long id, String title, String content, String memberEmail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
    }

}