package shop.tryit.domain.cart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* 장바구니 조회 시 전달 */
@Data
@NoArgsConstructor
public class CartListDto {

    private Long cartItemId; // 장바구니 상품 ID

    private Long itemId; // 상품 ID

    private String itemName; // 상품 이름

    private int itemPrice; // 상품 금액

    private int quantity; // 수량

    private String mainImageUrl; // 상품 메인이미지

    private int itemStock; // 상품 재고

    @Builder
    private CartListDto(Long cartItemId, Long itemId, String itemName, int itemPrice, int quantity, String mainImageUrl, int itemStock) {
        this.cartItemId = cartItemId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.mainImageUrl = mainImageUrl;
        this.itemStock = itemStock;
    }

}
