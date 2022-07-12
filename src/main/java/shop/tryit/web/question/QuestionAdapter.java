package shop.tryit.web.question;

import lombok.Getter;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.question.Question;

@Getter
public class QuestionAdapter {

    public static Question toEntity(QuestionSaveFormDto form, Member member) {
        return Question.of(form.getTitle(), form.getContent(), member);
    }

    public static Question toEntity(QuestionFormDto form, Member member) {
        return Question.of(form.getTitle(), form.getContent(), member);
    }

    public static QuestionFormDto toDto(Question question) {
        return new QuestionFormDto(question.getId(), question.getTitle(), question.getContent());
    }

}
