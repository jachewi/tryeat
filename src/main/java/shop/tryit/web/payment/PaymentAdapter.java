package shop.tryit.web.payment;

import shop.tryit.domain.payment.Payment;

public class PaymentAdapter {
    private PaymentAdapter() {
    }

    public static Payment toEntity(String merchant_uid, String amount) {
        return Payment.of(merchant_uid, amount);
    }

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .number(payment.getNumber())
                .totalPrice(payment.getTotalPrice())
                .build();
    }

}
