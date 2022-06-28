package shop.tryit.web.member;

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
import shop.tryit.domain.member.MemberService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

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

        Member member = MemberAdapter.toEntity(memberForm, address);

        service.saveMember(member);

        return "redirect:/";
    }

}