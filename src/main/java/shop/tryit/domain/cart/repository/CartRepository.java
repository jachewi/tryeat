package shop.tryit.domain.cart.repository;

import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.member.entity.Member;

public interface CartRepository {

    Long save(Cart cart);

    Cart findByMember(Member member);

}
