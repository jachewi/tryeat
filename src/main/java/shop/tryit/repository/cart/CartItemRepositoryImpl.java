package shop.tryit.repository.cart;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.cart.repository.CartItemRepository;
import shop.tryit.domain.item.entity.Item;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

    private final CartItemJpaRepository cartItemJpaRepository;

    @Override
    public Long save(CartItem cartItem) {
        return cartItemJpaRepository.save(cartItem).getId();
    }

    @Override
    public Optional<CartItem> findById(Long cartItemId) {
        return cartItemJpaRepository.findById(cartItemId);
    }

    @Override
    public CartItem findByCartAndItem(Cart cart, Item item) {
        return cartItemJpaRepository.findByCartAndItem(cart, item);
    }

    @Override
    public List<CartItem> findByCart(Cart cart) {
        return cartItemJpaRepository.findByCart(cart);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemJpaRepository.delete(cartItem);
    }
    
}
