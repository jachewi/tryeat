package shop.tryit.repository.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.repository.CartRepository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaRepository cartJpaRepository;

    @Override
    public Long save(Cart cart) {
        return cartJpaRepository.save(cart).getId();
    }

}
