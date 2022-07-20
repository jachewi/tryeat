package shop.tryit.domain.cart;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.service.CartService;
import shop.tryit.domain.member.Member;
import shop.tryit.repository.cart.CartJpaRepository;

@Transactional
@SpringBootTest
class CartServiceTests {

    @Autowired
    CartJpaRepository cartJpaRepository;

    @Autowired
    CartService sut;

    @Test
    void 장바구니_등록() {
        // given
        Member member = Member.builder().build();
        Cart cart = Cart.from(member);

        // when
        Long savedId = sut.register(cart);

        // then
        assertTrue(cartJpaRepository.findById(savedId).isPresent());
    }

}