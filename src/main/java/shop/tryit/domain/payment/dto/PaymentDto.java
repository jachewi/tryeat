package shop.tryit.domain.payment.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDto {

    @NotNull
    private Long number; //주문번호 merchant_uid

    @NotNull
    private String totalPrice; // 총 금액 amount

    @Builder
    private PaymentDto(Long number, String totalPrice) {
        this.number = number;
        this.totalPrice = totalPrice;
    }

}

