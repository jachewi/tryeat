package shop.tryit.web.question;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionSearchCondition;
import shop.tryit.domain.question.QuestionSearchDto;
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
                              BindingResult bindingResult,
                              @AuthenticationPrincipal User user
    ) {

        log.info("bindingResult={}", bindingResult);

        if (bindingResult.hasErrors()) {
            return "questions/register";
        }

        String userEmail = user.getUsername();
        Member member = memberService.findMember(userEmail);
        log.info("user = '{}'", user);
        log.info("userEmail = '{}'", userEmail);

        Question question = QuestionAdapter.toEntity(questionSaveFormDto, member);
        questionService.register(question);
        return "redirect:/questions";
    }

    @GetMapping
    public String searchQuestion(Model model,
                                 @ModelAttribute QuestionSearchCondition questionSearchCondition,
                                 Pageable pageable
    ) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 2);
        Page<QuestionSearchDto> questions = questionService.searchList(questionSearchCondition, pageRequest);

        int startPage = Math.max(1, questions.getPageable().getPageNumber() - 4);
        int endPage = Math.min(questions.getTotalPages(), questions.getPageable().getPageNumber() + 4);

        model.addAttribute("questions", questions);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        log.info("questionSearchCondition = '{}'", questionSearchCondition);
        log.info("questions = '{}'", questions);
        log.info("questions.size = '{}'", questions.getContent().size());

        return "questions/list";
    }

}
