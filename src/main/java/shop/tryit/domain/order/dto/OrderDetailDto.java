package shop.tryit.domain.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailDto {

    private Long merchant_uid;
    private Long itemId;
    private int orderQuantity;

}
