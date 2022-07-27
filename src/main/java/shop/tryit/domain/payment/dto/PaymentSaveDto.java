package shop.tryit.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentSaveDto {
    private Long merchant_uid;
    private String amount;

}
