package shop.tryit.web.answer;

import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.answer.dto.AnswerFormDto;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.question.entity.Question;

public class AnswerAdapter {

    public static Answer toEntity(AnswerFormDto answerFormDto, Question question, Member member) {
        return Answer.of(answerFormDto.getContent(), question, member);
    }

    public static AnswerFormDto toForm(Answer answer) {
        return new AnswerFormDto(answer.getId(), answer.getContent(), answer.getCreateDate());
    }

}