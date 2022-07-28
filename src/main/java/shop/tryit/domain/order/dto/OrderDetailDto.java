package shop.tryit.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailDto {

    private Long itemId;
    private int orderQuantity;

}
