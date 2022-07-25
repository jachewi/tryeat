package shop.tryit.web.payment;

import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.payment.Payment;

public class PaymentAdapter {
    private PaymentAdapter() {
    }

    public static Payment toEntity(Long number, String totalPrice) {
        return Payment.of(number, totalPrice);
    }

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .number(payment.getNumber())
                .totalPrice(payment.getTotalPrice())
                .build();
    }

    public static PaymentResponseDto toDto(Item item, Image mainImage, Member member, PaymentRequsetDto paymentRequsetDto) {
        return PaymentResponseDto.builder()
                .itemId(item.getId())
                .mainImage(mainImage)
                .itemName(item.getName())
                .quantity(paymentRequsetDto.getQuantity())
                .totalPrice(item.getPrice() * paymentRequsetDto.getQuantity())
                .memberName(member.getName())
                .memberPhone(member.getPhoneNumber())
                .zipcode(member.addressZipcode())
                .street_address(member.addressStreet())
                .jibeon_address(member.addressJibeon())
                .detail_address(member.addressDetail())
                .build();
    }

}
