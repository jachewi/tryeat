package shop.tryit.domain.cart;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.service.CartService;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;
import shop.tryit.repository.cart.CartJpaRepository;

@Transactional
@SpringBootTest
class CartServiceTests {

    @Autowired
    CartService sut;

    @Autowired
    CartJpaRepository cartJpaRepository;

    @Autowired
    MemberRepository memberRepository;

    private Member saveMember() {
        Member member = Member.builder()
                .email("test@test.com")
                .build();
        memberRepository.save(member);
        return member;
    }

    @Test
    void 장바구니_조회() {
        // given
        Member member = saveMember();

        // when
        Cart cart = sut.findCart(member.getEmail());

        // then
        assertNotNull(cartJpaRepository.findById(cart.getId()));
    }

}