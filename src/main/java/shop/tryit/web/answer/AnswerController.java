package shop.tryit.web.answer;

import javax.validation.Valid;
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
import shop.tryit.domain.answer.dto.AnswerFormDto;
import shop.tryit.domain.question.service.QnAFacade;

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
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return String.format("redirect:/questions/%s", questionId);
        }

        qnAFacade.answerRegister(user, questionId, answerFormDto);
        return String.format("redirect:/questions/%s", questionId);
    }

    @PostMapping("/delete/{answerId}")
    public String delete(@PathVariable Long answerId) {
        qnAFacade.delete(answerId);
        Long questionId = qnAFacade.findQuestionIdByAnswerId(answerId);
        return String.format("redirect:/questions/%s", questionId);
    }

}
