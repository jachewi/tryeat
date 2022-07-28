package shop.tryit.web.notice;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
import shop.tryit.domain.common.Pages;
import shop.tryit.domain.notice.dto.NoticeSaveFormDto;
import shop.tryit.domain.notice.dto.NoticeSearchFormDto;
import shop.tryit.domain.notice.dto.NoticeUpdateFormDto;
import shop.tryit.domain.notice.dto.NoticeViewFormDto;
import shop.tryit.domain.notice.service.NoticeFacade;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeFacade noticeFacade;

    @GetMapping("/new")
    public String saveForm(@ModelAttribute NoticeSaveFormDto noticeSaveFormDto) {
        return "notices/register";
    }

    @PostMapping("/new")
    public String save(@Valid @ModelAttribute NoticeSaveFormDto noticeSaveFormDto,
                       BindingResult bindingResult,
                       @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = '{}'", bindingResult);
            return "notices/register";
        }

        Long savedId = noticeFacade.register(user, noticeSaveFormDto);
        return String.format("redirect:/notices/%s", savedId);
    }

    @GetMapping("/{noticeId}")
    public String findOne(@PathVariable Long noticeId, Model model) {
        NoticeViewFormDto noticeViewFormDto = noticeFacade.toViewForm(noticeId);
        model.addAttribute("noticeViewFormDto", noticeViewFormDto);
        return "notices/detail";
    }

    @GetMapping("/{noticeId}/update")
    public String updateForm(@PathVariable Long noticeId, Model model) {
        NoticeUpdateFormDto noticeUpdateFormDto = noticeFacade.toUpdateForm(noticeId);
        model.addAttribute("noticeUpdateFormDto", noticeUpdateFormDto);
        return "notices/update";
    }

    @PostMapping("/{noticeId}/update")
    public String update(@PathVariable Long noticeId,
                         @ModelAttribute NoticeUpdateFormDto noticeUpdateFormDto,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = '{}'", bindingResult);
            return "notices/register";
        }

        noticeFacade.update(user, noticeId, noticeUpdateFormDto);
        return String.format("redirect:/notices/%s", noticeId);
    }

    @PostMapping("/{noticeId}/delete")
    public String delete(@PathVariable Long noticeId) {
        noticeFacade.delete(noticeId);
        return "redirect:/notices";
    }

    @GetMapping
    public String search(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<NoticeSearchFormDto> notices = noticeFacade.searchNotices(page);
        List<Integer> pages = Pages.of(notices, 4).getPages();
        model.addAttribute("notices", notices);
        model.addAttribute("pages", pages);
        return "notices/list";
    }

}