package shop.tryit.web.member;

import shop.tryit.domain.common.Address;
import shop.tryit.domain.member.Member;

public class MemberAdapter {
    public static Member toEntity(MemberFormDto memberFormDto, Address address) {
        Member member = Member.builder()
                .email(memberFormDto.getEmail())
                .name(memberFormDto.getName())
                .password(memberFormDto.getPassword1())
                .address(address)
                .phoneNumber(memberFormDto.getPhone())
                .build();

        return member;
    }

    public static MemberFormDto toDto(Member member) {
        MemberFormDto memberFormDto = MemberFormDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password1(member.getPassword())
                .phone(member.getPhoneNumber())
                .build();

        return memberFormDto;
    }
}