package shop.tryit.web.member;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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
    public String newMember(@Valid @ModelAttribute("memberForm") MemberFormDto memberForm, BindingResult bindingResult) {

        if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "비밀번호가 일치하지 않습니다.");
        }

        //Todo 실 DB table 구축 후 admin 토큰 주입 예정
        MemberRole role = MemberRole.USER;
        if (memberForm.isAdmin()){
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

        Address address = Address.builder()
                .zipcode(memberForm.getZipcode())
                .street_address(memberForm.getStreet_address())
                .jibeon_address(memberForm.getJibeon_address())
                .detail_address(memberForm.getDetail_address())
                .build();

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


}