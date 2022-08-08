package shop.tryit.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/* 결제 할 상품의 데이터를 받음 */
@Data
@NoArgsConstructor
public class PaymentRequestDto {

    private Long itemId;

    private int quantity;

}
