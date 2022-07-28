package shop.tryit.domain.notice.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeSearchFormDto {

    private Long id;
    private String memberEmail;
    private String title;
    private LocalDateTime createDate;

    @Builder
    private NoticeSearchFormDto(Long id, String memberEmail, String title, LocalDateTime createDate) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.title = title;
        this.createDate = createDate;
    }

}
