package shop.tryit.domain.payment.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 결제 할 상품 목록 데이터를 받음 */
@Data
@NoArgsConstructor
public class PaymentRequestListDto {

    private List<PaymentRequestDto> payments;

}
