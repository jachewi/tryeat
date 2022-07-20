package shop.tryit.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.cart.entity.Cart;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {

}
