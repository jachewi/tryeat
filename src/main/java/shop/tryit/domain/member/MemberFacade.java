package shop.tryit.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import shop.tryit.domain.common.Address;
import shop.tryit.domain.member.dto.MemberFormDto;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;

    /**
     * 회원 가입
     */
    public Long register(MemberFormDto memberForm) {
        MemberRole role = MemberRole.ROLE_USER;
        Address address = toAddress(memberForm);
        Member member = toEntity(memberForm, address, role);
        return memberService.saveMember(member);
    }

    /**
     * 회원 수정 폼
     */
    public MemberFormDto updateForm(User user) {
        String userEmail = user.getUsername();
        Member member = memberService.findMember(userEmail);
        return toDto(member);
    }

    /**
     * 회원 수정 업데이트
     */
    public String update(MemberFormDto memberForm) {
        Address address = toAddress(memberForm);
        Member member = toEntity(memberForm, address);
        return memberService.update(member.getEmail(), member);
    }

    public Member toEntity(MemberFormDto memberFormDto, Address address, MemberRole role) {
        return Member.builder()
                .email(memberFormDto.getEmail())
                .name(memberFormDto.getName())
                .password(memberFormDto.getPassword1())
                .address(address)
                .phoneNumber(memberFormDto.getPhone())
                .role(role)
                .build();
    }

    public Member toEntity(MemberFormDto memberFormDto, Address address) {
        return Member.builder()
                .email(memberFormDto.getEmail())
                .name(memberFormDto.getName())
                .password(memberFormDto.getPassword1())
                .address(address)
                .phoneNumber(memberFormDto.getPhone())
                .build();
    }

    public Address toAddress(MemberFormDto memberForm) {
        return Address.builder()
                .zipcode(memberForm.getZipcode())
                .street_address(memberForm.getStreet_address())
                .jibeon_address(memberForm.getJibeon_address())
                .detail_address(memberForm.getDetail_address())
                .build();
    }

    public MemberFormDto toDto(Member member) {
        return MemberFormDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhoneNumber())
                .zipcode(member.getAddress().getZipcode())
                .jibeon_address(member.getAddress().getJibeon_address())
                .street_address(member.getAddress().getStreet_address())
                .detail_address(member.getAddress().getDetail_address())
                .build();
    }

}
