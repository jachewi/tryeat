package shop.tryit.domain.order.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 상품상세에서 단건 주문을 했을 경우
 */
@Data
@NoArgsConstructor
public class OrderFormDto {

    // View에서 넘겨받는 값
    private Long itemId;
    private int orderQuantity; // 주문 수량

    // DB에서 조회해오는 값
    private String itemName;  // 상품이름
    private int itemPrice; // 상품 가격

    @Builder
    private OrderFormDto(Long itemId, String itemName, int orderQuantity, int itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.orderQuantity = orderQuantity;
        this.itemPrice = itemPrice;
    }

}
