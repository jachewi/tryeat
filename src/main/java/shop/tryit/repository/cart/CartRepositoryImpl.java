package shop.tryit.repository.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.repository.CartRepository;
import shop.tryit.domain.member.entity.Member;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaRepository cartJpaRepository;

    @Override
    public Long save(Cart cart) {
        return cartJpaRepository.save(cart).getId();
    }

    @Override
    public Cart findByMember(Member member) {
        return cartJpaRepository.findByMember(member);
    }

}
