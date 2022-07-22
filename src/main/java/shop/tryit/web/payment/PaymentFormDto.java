package shop.tryit.web.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentFormDto {

    private Long itemId;

    private int quantity;

}
