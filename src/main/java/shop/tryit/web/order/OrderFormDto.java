package shop.tryit.web.order;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderFormDto {

    /**
     * 상품상세에서 단품 주문을 했을 경우
     */
    private Long itemId;
    private String itemName;  //상품이름
    private int orderQuantity; //주문 수량
    private int itemPrice; //상품 가격

    @Builder
    private OrderFormDto(Long itemId, String itemName, int orderQuantity, int itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.orderQuantity = orderQuantity;
        this.itemPrice = itemPrice;
    }

}
