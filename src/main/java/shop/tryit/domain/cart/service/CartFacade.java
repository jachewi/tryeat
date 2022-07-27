package shop.tryit.domain.cart.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import shop.tryit.domain.cart.dto.CartItemDto;
import shop.tryit.domain.cart.dto.CartListDto;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.ImageService;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemService;

@Component
@RequiredArgsConstructor
public class CartFacade {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ItemService itemService;
    private final ImageService imageService;

    /**
     * 장바구니 상품 추가
     */
    public Long addCartItem(CartItemDto cartItemDto, User user) {
        Cart cart = cartService.findCart(user.getUsername());
        Item item = itemService.findOne(cartItemDto.getItemId());

        CartItem cartItem = toEntity(cartItemDto, item, cart);

        return cartItemService.addCartItem(cartItem);
    }

    /**
     * 장바구니 상품 조회
     */
    public List<CartListDto> findCartItems(User user) {
        Cart cart = cartService.findCart(user.getUsername());
        List<CartItem> cartItems = cartItemService.findCartItemList(cart);

        List<Image> mainImages = cartItems.stream()
                .map(CartItem::getItemId) // CartItem -> Long
                .map(imageService::getMainImage)// Long -> Image
                .collect(toList());

        return toDto(cartItems, mainImages);
    }

    public CartItem toEntity(CartItemDto cartItemDto, Item item, Cart cart) {
        return CartItem.builder()
                .cart(cart)
                .item(item)
                .quantity(cartItemDto.getQuantity())
                .build();
    }

    public CartListDto toDto(CartItem cartItem, Image mainImage) {
        return CartListDto.builder()
                .itemId(cartItem.getItemId())
                .itemName(cartItem.getItemName())
                .itemPrice(cartItem.getItemPrice())
                .quantity(cartItem.getQuantity())
                .mainImage(mainImage)
                .build();
    }

    public List<CartListDto> toDto(List<CartItem> cartItems, List<Image> mainImages) {
        List<CartListDto> cartListDtos = new ArrayList<>();

        for (int i = 0; i < cartItems.size(); i++) {
            cartListDtos.add(toDto(cartItems.get(i), mainImages.get(i)));
        }

        return cartListDtos;
    }

}
