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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.tryit.domain.answer.AnswerService;
import shop.tryit.domain.common.Pages;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionSearchCondition;
import shop.tryit.domain.question.QuestionSearchDto;
import shop.tryit.domain.question.QuestionService;
import shop.tryit.web.answer.AnswerAdapter;
import shop.tryit.web.answer.AnswerFormDto;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
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
        Pages<QuestionSearchDto> pages = Pages.of(questions, 4);

        model.addAttribute("questions", questions);
        model.addAttribute("pages", pages.getPages());

        log.info("questionSearchCondition = '{}'", questionSearchCondition);
        log.info("questions = '{}'", questions);
        log.info("questions.size = '{}'", questions.getContent().size());

        return "questions/list";
    }

    @GetMapping("/{questionId}")
    public String findOne(@PathVariable Long questionId,
                          Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @ModelAttribute AnswerFormDto answerFormDto) {
        Question question = questionService.findOne(questionId);
        QuestionFormDto questionFormDto = QuestionAdapter.toDto(question);

        Page<AnswerFormDto> answers = answerService.findAnswersByQuestionId(questionId, page)
                .map(AnswerAdapter::toForm);

        Pages<AnswerFormDto> pages = Pages.of(answers, 4);

        log.info("questionFormDto='{}'", questionFormDto);
        log.info("answers='{}'", answers);

        model.addAttribute("pages", pages.getPages());
        model.addAttribute("questionFormDto", questionFormDto);
        model.addAttribute("answers", answers);

        return "questions/detail-view";
    }

    @GetMapping("/{questionId}/update")
    public String updateForm(@PathVariable Long questionId,
                             @ModelAttribute QuestionFormDto questionFormDto) {
        return "questions/update";
    }

    @PostMapping("/{questionId}/update")
    public String update(@PathVariable Long questionId,
                         @Valid @ModelAttribute QuestionFormDto questionFormDto,
                         @AuthenticationPrincipal User user
    ) {

        String userEmail = user.getUsername();
        Member member = memberService.findMember(userEmail);
        Question newQuestion = QuestionAdapter.toEntity(questionFormDto, member);
        questionService.update(questionId, newQuestion);
        return String.format("redirect:/questions/%s", questionId);
    }

}
