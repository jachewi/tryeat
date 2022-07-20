package shop.tryit.domain.cart.service;

import static java.util.Objects.nonNull;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.cart.repository.CartItemRepository;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    /**
     * 장바구니에 상품 추가
     */
    @Transactional
    public Long addCartItem(CartItem cartItem) {
        // 장바구니에 담을 상품 엔티티 조회
        Item item = itemRepository.findById(cartItem.getItem().getId())
                .orElseThrow(() -> new IllegalStateException("해당 상품을 찾을 수 없습니다."));

        // 장바구니에 해당 상품이 있는지 확인
        CartItem savedCartItem = cartItemRepository.findByCartAndItem(cartItem.getCart(), item);

        // 상품이 이미 있을 경우, 개수 증가
        if (nonNull(savedCartItem)) {
            savedCartItem.addCount(cartItem.getCount());
            return savedCartItem.getId();
        }

        // 상품이 없을 경우, 장바구니에 상품 추가
        savedCartItem = CartItem.builder()
                .cart(cartItem.getCart())
                .item(cartItem.getItem())
                .count(cartItem.getCount())
                .build();
        cartItemRepository.save(savedCartItem);

        return savedCartItem.getId();
    }

}
