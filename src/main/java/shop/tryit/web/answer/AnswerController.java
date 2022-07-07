package shop.tryit.web.answer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.answer.AnswerService;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final MemberService memberService;

    @PostMapping("/new/{questionId}")
    public String register(@PathVariable Long questionId,
                           @AuthenticationPrincipal User user,
                           @ModelAttribute AnswerFormDto answerFormDto,
                           BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "questions/register";
        }

        Member member = memberService.findMember(user.getUsername());
        Question question = questionService.findOne(questionId);
        Answer answer = AnswerAdapter.toEntity(answerFormDto, question, member);
        answerService.register(answer);

        return String.format("redirect:/questions/%s", questionId);
    }

}
