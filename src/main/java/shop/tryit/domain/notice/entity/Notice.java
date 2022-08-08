package shop.tryit.domain.notice.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.common.BaseTimeEntity;
import shop.tryit.domain.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder
    private Notice(Long id, String title, String content, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Long update(String title, String content) {
        this.title = title;
        this.content = content;

        return id;
    }

    public String getUserEmail() {
        return member.getEmail();
    }

}
