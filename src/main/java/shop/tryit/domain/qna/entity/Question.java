package shop.tryit.domain.qna.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.common.BaseTimeEntity;
import shop.tryit.domain.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String password;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Question(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Question of(String title, String content, Member member) {
        return new Question(title, content, member);
    }

    public void update(String title, String content) {
        changeTitle(title);
        changeContent(content);
    }

    public void allocatePassword(String password) {
        this.password = password;
    }

    private void changeTitle(String title) {
        this.title = title;
    }

    private void changeContent(String content) {
        this.content = content;
    }

}
