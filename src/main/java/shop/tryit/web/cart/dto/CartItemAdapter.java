package shop.tryit.web.cart.dto;

import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.item.Image;
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

    public static CartListDto toDto(CartItem cartItem, Image mainImage) {
        return CartListDto.builder()
                .itemId(cartItem.getItemId())
                .itemName(cartItem.getItemName())
                .itemPrice(cartItem.getItemPrice())
                .quantity(cartItem.getQuantity())
                .mainImage(mainImage)
                .build();
    }

    public static List<CartListDto> toDto(List<CartItem> cartItems, List<Image> mainImages) {
        List<CartListDto> cartListDtos = new ArrayList<>();

        for (int i = 0; i < cartItems.size(); i++) {
            cartListDtos.add(toDto(cartItems.get(i), mainImages.get(i)));
        }

        return cartListDtos;
    }

}
