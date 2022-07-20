package shop.tryit.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.cart.entity.CartItem;

public interface CartItemJpaRepository extends JpaRepository<CartItem, Long> {

}
