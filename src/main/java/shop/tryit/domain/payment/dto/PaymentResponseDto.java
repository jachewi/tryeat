package shop.tryit.domain.payment.dto;

import lombok.Builder;
import lombok.Data;
import shop.tryit.domain.item.entity.Image;

@Data
public class PaymentResponseDto {

    //상품 정보
    private Long itemId;
    private String itemName;
    private int quantity;
    private int totalPrice;

    //상품 메인이미지
    private Image mainImage;

    //회원 정보
    private String memberName;
    private String memberPhone;

    //주소
    private Long zipcode; //우편번호
    private String street_address; //도로명 주소
    private String jibeon_address; //지번 주소
    private String detail_address; //상세 주소

    @Builder
    private PaymentResponseDto(Long itemId, String itemName, int quantity,
                               int totalPrice, Image mainImage, String memberName, String memberPhone,
                               Long zipcode, String street_address, String jibeon_address, String detail_address) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.mainImage = mainImage;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.zipcode = zipcode;
        this.street_address = street_address;
        this.jibeon_address = jibeon_address;
        this.detail_address = detail_address;
    }

}
