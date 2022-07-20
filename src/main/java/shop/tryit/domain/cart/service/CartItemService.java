package shop.tryit.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.cart.repository.CartItemRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    /**
     * 장바구니 상품 등록
     */
    public Long register(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

}
