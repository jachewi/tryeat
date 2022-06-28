package shop.tryit.web.question;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @GetMapping("/new")
    public String newQuestionForm(@ModelAttribute QuestionSaveFormDto questionSaveFormDto) {
        return "questions/register";
    }

    @PostMapping("/new")
    public String newQuestion(@Valid @ModelAttribute QuestionSaveFormDto questionSaveFormDto,
                              BindingResult bindingResult) {

        log.info("bindingResult={}", bindingResult);

        if (bindingResult.hasErrors()) {
            return "questions/register";
        }

        // TODO : 나중에 security 이용하여 회원 이름 조회하기
        Member member = Member.builder().password("123").build();
        memberService.saveMember(member);

        Question question = QuestionAdapter.toEntity(questionSaveFormDto, member);
        questionService.register(question);
        return "redirect:/";
    }

}
