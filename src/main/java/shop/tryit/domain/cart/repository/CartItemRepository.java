package shop.tryit.domain.cart.repository;

import java.util.List;
import java.util.Optional;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.item.entity.Item;

public interface CartItemRepository {

    Long save(CartItem cartItem);

    Optional<CartItem> findById(Long cartItemId);

    CartItem findByCartAndItem(Cart cart, Item item);

    List<CartItem> findByCart(Cart cart);

    void deleteByCartItem(CartItem cartItem);

    Optional<CartItem> findByItem(Item item);

}
