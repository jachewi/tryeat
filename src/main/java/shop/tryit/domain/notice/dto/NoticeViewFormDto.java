package shop.tryit.domain.notice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeViewFormDto {

    private Long id;
    private String title;
    private String content;
    private String memberEmail;

    @Builder
    private NoticeViewFormDto(Long id, String title, String content, String memberEmail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberEmail = memberEmail;
    }

}
