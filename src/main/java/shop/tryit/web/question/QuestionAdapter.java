package shop.tryit.web.question;

import lombok.Getter;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.question.Question;

@Getter
public class QuestionAdapter {

    public static Question toEntity(QuestionSaveFormDto form, Member member) {
        return Question.of(form.getTitle(), form.getContent(), member);
    }

}
