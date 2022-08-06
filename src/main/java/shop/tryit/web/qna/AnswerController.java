package shop.tryit.web.qna;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import shop.tryit.domain.qna.dto.AnswerFormDto;
import shop.tryit.domain.qna.service.QnAFacade;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

    private final QnAFacade qnAFacade;

    @PostMapping("/new/{questionId}")
    public String register(@PathVariable Long questionId,
                           @AuthenticationPrincipal User user,
                           @Valid @ModelAttribute AnswerFormDto answerFormDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            model.addAttribute("questionFormDto", qnAFacade.toDto(questionId));
            return "/qna/detail-view";
        }

        qnAFacade.answerRegister(user, questionId, answerFormDto);
        return String.format("redirect:/qna/%s", questionId);
    }

    @PostMapping("/delete/{answerId}")
    public String delete(@PathVariable Long answerId) {
        Long questionId = qnAFacade.findQuestionIdByAnswerId(answerId);
        qnAFacade.delete(answerId);
        return String.format("redirect:/qna/%s", questionId);
    }

    @GetMapping("/{answerId}/update")
    public String updateForm(@PathVariable Long answerId,
                             Model model,
                             @AuthenticationPrincipal User user) {
        if (qnAFacade.checkUpdate(user, answerId)) {
            model.addAttribute("answerFormDto", qnAFacade.toForm(answerId));
        }

        return "/qna/answer-update";
    }

    @PostMapping("/{answerId}/update")
    public String update(@PathVariable Long answerId,
                         @AuthenticationPrincipal User user,
                         @Valid @ModelAttribute AnswerFormDto answerFormDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/qna/answer-update";
        }

        Long questionId = qnAFacade.findQuestionIdByAnswerId(answerId);
        qnAFacade.updateAnswer(answerId, answerFormDto);
        return String.format("redirect:/qna/%s", questionId);
    }

}
