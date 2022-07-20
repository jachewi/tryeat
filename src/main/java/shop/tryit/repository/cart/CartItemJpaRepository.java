package shop.tryit.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.item.Item;

public interface CartItemJpaRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndItem(Cart cart, Item item);

}
