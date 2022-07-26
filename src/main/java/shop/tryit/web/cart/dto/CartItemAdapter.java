package shop.tryit.web.cart.dto;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.item.Item;

@NoArgsConstructor(access = PRIVATE)
public class CartItemAdapter {

    public static CartItem toEntity(CartItemDto cartItemDto, Item item, Cart cart) {
        return CartItem.builder()
                .cart(cart)
                .item(item)
                .quantity(cartItemDto.getQuantity())
                .build();
    }

}
