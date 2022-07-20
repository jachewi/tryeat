package shop.tryit.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.repository.CartRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;

    /**
     * 장바구니 등록
     */
    public Long register(Cart cart) {
        return cartRepository.save(cart);
    }

}
