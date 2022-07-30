package shop.tryit.domain.cart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.entity.Image;

/* 장바구니 조회 시 전달 */
@Data
@NoArgsConstructor
public class CartListDto {

    private Long cartItemId; // 장바구니 상품 ID

    private Long itemId; // 상품 ID

    private String itemName; // 상품 이름

    private int itemPrice; // 상품 금액

    private int quantity; // 수량

    private Image mainImage; // 상품 메인이미지

    private int totalPrice; // 상품 금액 * 수량

    @Builder
    private CartListDto(Long cartItemId, Long itemId, String itemName, int itemPrice, int quantity, Image mainImage) {
        this.cartItemId = cartItemId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.mainImage = mainImage;
        this.totalPrice = itemPrice * quantity;
    }

}
