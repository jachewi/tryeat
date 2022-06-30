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
                .phone(member.getPhoneNumber())
                .zipcode(member.getAddress().getZipcode())
                .jibeon_address(member.getAddress().getJibeon_address())
                .street_address(member.getAddress().getStreet_address())
                .detail_address(member.getAddress().getDetail_address())
                .build();

        return memberFormDto;
    }
}