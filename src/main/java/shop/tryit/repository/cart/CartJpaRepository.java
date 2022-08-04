package shop.tryit.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.member.entity.Member;

public interface CartJpaRepository extends JpaRepository<Cart, Long> {
    Cart findByMember(Member member);

}
