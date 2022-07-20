package shop.tryit.repository.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.cart.repository.CartItemRepository;
import shop.tryit.domain.item.Item;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

    private final CartItemJpaRepository cartItemJpaRepository;

    @Override
    public Long save(CartItem cartItem) {
        return cartItemJpaRepository.save(cartItem).getId();
    }

    @Override
    public CartItem findByCartAndItem(Cart cart, Item item) {
        return cartItemJpaRepository.findByCartAndItem(cart, item);
    }

}
