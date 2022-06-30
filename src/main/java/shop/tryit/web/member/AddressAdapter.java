package shop.tryit.web.member;

import shop.tryit.domain.common.Address;

public class AddressAdapter {
    public static Address toAddress(MemberFormDto memberForm){
        Address address = Address.builder()
                .zipcode(memberForm.getZipcode())
                .street_address(memberForm.getStreet_address())
                .jibeon_address(memberForm.getJibeon_address())
                .detail_address(memberForm.getDetail_address())
                .build();

        return address;
    }
}
