package shop.tryit.web.cart.dto;

import javax.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 장바구니에 담을 상품의 id와 수량을 받음 */
@Data
@NoArgsConstructor
public class CartItemDto {

    private Long itemId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요.")
    private int quantity;

    private CartItemDto(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public static CartItemDto of(Long itemId, int quantity) {
        return new CartItemDto(itemId, quantity);
    }

}
