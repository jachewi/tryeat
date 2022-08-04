package shop.tryit.domain.member.entity;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Address {
    private Long zipcode; //우편번호
    private String street_address; //도로명 주소
    private String jibeon_address; //지번 주소
    private String detail_address; //상세 주소

    @Builder
    public Address(Long zipcode, String street_address, String jibeon_address, String detail_address) {
        this.zipcode = zipcode;
        this.street_address = street_address;
        this.jibeon_address = jibeon_address;
        this.detail_address = detail_address;
    }

}
