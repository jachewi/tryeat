package shop.tryit.web.member;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.common.Address;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRole;
import shop.tryit.domain.member.MemberService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;
    //Todo 실 DB table 구축 후 admin 토큰 주입 예정
    private static final String ADMIN_TOKEN = "$2a$10$THOEy8e4waopu4SvUc7o7uSZYGJT8gkdwL22eN3i7gz4ewJ.gONeS";

    /**
     * 회원 가입
     */
    @GetMapping("/new")
    public String newMemberForm(@ModelAttribute("memberForm") MemberFormDto memberForm) {
        log.info("member controller");
        return "/members/register";
    }

    @PostMapping("/new")
    public String newMember(@Valid @ModelAttribute("memberForm") MemberFormDto memberForm,
                            BindingResult bindingResult) {

        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
        }

        //Todo 실 DB table 구축 후 admin 토큰 주입 예정
        MemberRole role = MemberRole.USER;
        if (memberForm.isAdmin()) {
            if (!memberForm.getPassword1().equals(ADMIN_TOKEN)) {
                bindingResult.rejectValue("password1", "ADMIN_TOKENInCorrect",
                        "관리자 암호가 틀려 등록이 불가합니다.");
            }
            role = MemberRole.ADMIN;
        }

        if (bindingResult.hasErrors()) {
            log.info("member controller post");
            return "/members/register";
        }

        Address address = AddressAdapter.toAddress(memberForm);

        Member member = MemberAdapter.toEntity(memberForm, address, role);
        service.saveMember(member);

        return "redirect:/";
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String login_form() {
        log.info("member login controller");
        return "/members/login-form";
    }

    /**
     * 회원 프로필
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String editMemberForm(@AuthenticationPrincipal User user, Model model) {
        log.info("회원 수정 폼으로 이동");
        String userEmail = user.getUsername();
        Member member = service.findMember(userEmail);
        MemberFormDto memberFormDto = MemberAdapter.toDto(member);
        model.addAttribute("memberForm", memberFormDto);
        return "/members/edit";
    }

    @PostMapping("/edit")
    public String editMember(@Valid @ModelAttribute("memberForm") MemberFormDto memberForm,
                             BindingResult bindingResult){
        log.info("회원 수정으로 이동");
        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("member controller post");
            return "/members/edit";
        }

        Address address = AddressAdapter.toAddress(memberForm);

        Member member = MemberAdapter.toEntity(memberForm, address);
        service.update(member.getEmail(), member);

        return "/members/edit";
    }


}