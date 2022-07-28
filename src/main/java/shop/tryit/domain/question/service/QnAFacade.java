package shop.tryit.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.answer.dto.AnswerFormDto;
import shop.tryit.domain.answer.service.AnswerService;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.question.dto.QuestionCheckPasswordFormDto;
import shop.tryit.domain.question.dto.QuestionFormDto;
import shop.tryit.domain.question.dto.QuestionSaveFormDto;
import shop.tryit.domain.question.dto.QuestionSearchCondition;
import shop.tryit.domain.question.dto.QuestionSearchDto;
import shop.tryit.domain.question.entity.Question;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnAFacade {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    public void questionRegister(QuestionSaveFormDto questionSaveFormDto, User user) {
        String userEmail = user.getUsername();
        Member member = memberService.findMember(userEmail);

        Question question = toEntity(questionSaveFormDto, member);
        questionService.register(question, questionSaveFormDto.getPassword());
    }

    public Page<QuestionSearchDto> searchQuestion(Pageable pageable, QuestionSearchCondition questionSearchCondition) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 2);
        return questionService.searchList(questionSearchCondition, pageRequest);
    }

    public boolean checkPassword(Long questionId, QuestionCheckPasswordFormDto questionCheckPasswordFormDto) {
        Question question = questionService.findOne(questionId);
        return questionService.checkPassword(questionCheckPasswordFormDto.getPassword(), question);
    }

    public void answerRegister(User user, Long questionId, AnswerFormDto answerFormDto) {
        Member member = memberService.findMember(user.getUsername());
        Question question = questionService.findOne(questionId);
        Answer answer = toEntity(answerFormDto, question, member);
        answerService.register(answer);
    }

    public void delete(Long answerId) {
        Answer answer = answerService.findById(answerId);
        answerService.delete(answer);
    }

    public Long findQuestionIdByAnswerId(Long answerId) {
        return answerService.findById(answerId)
                .getQuestionId();
    }

    public Page<AnswerFormDto> findAnswersByQuestionId(Long questionId, int page) {
        return answerService.findAnswersByQuestionId(questionId, page)
                .map(this::toForm);
    }

    public void update(Long questionId, QuestionFormDto questionFormDto, User user) {
        String userEmail = user.getUsername();
        Member member = memberService.findMember(userEmail);
        Question newQuestion = toEntity(questionFormDto, member);
        questionService.update(questionId, newQuestion);
    }

    public QuestionFormDto toDto(Long questionId) {
        Question question = questionService.findOne(questionId);
        return new QuestionFormDto(question.getId(), question.getTitle(), question.getContent());
    }

    public AnswerFormDto toForm(Answer answer) {
        return new AnswerFormDto(answer.getId(), answer.getContent(), answer.getCreateDate());
    }

    private Question toEntity(QuestionSaveFormDto form, Member member) {
        return Question.of(form.getTitle(), form.getContent(), member);
    }

    private Question toEntity(QuestionFormDto form, Member member) {
        return Question.of(form.getTitle(), form.getContent(), member);
    }

    private Answer toEntity(AnswerFormDto answerFormDto, Question question, Member member) {
        return Answer.of(answerFormDto.getContent(), question, member);
    }

}
