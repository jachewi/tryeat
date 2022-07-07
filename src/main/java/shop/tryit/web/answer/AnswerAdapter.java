package shop.tryit.web.answer;

import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.question.Question;

public class AnswerAdapter {

    public static Answer toEntity(AnswerFormDto answerFormDto, Question question, Member member) {
        return Answer.of(answerFormDto.getContent(), question, member);
    }

}