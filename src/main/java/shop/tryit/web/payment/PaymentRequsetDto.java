package shop.tryit.web.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentRequsetDto {

    private Long itemId;

    private int quantity;

}
