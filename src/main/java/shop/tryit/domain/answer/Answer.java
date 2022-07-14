package shop.tryit.domain.answer;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.common.BaseTimeEntity;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.question.Question;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    private Question question;

    @ManyToOne(fetch = LAZY)
    private Member member;

    private Answer(String content, Question question, Member member) {
        this.content = content;
        this.question = question;
        this.member = member;
    }

    public static Answer of(String content, Question question, Member member) {
        return new Answer(content, question, member);
    }

    public void update(String content) {
        this.content = content;
    }

    public Long getQuestionId() {
        return question.getId();
    }

}

