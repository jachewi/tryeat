package shop.tryit.web.member;

import shop.tryit.domain.common.Address;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRole;

public class MemberAdapter {
    public static Member toEntity(MemberFormDto memberFormDto, Address address, MemberRole role) {
        Member member = Member.builder()
                .email(memberFormDto.getEmail())
                .name(memberFormDto.getName())
                .password(memberFormDto.getPassword1())
                .address(address)
                .phoneNumber(memberFormDto.getPhone())
                .role(role)
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