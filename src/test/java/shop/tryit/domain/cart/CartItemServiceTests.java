package shop.tryit.domain.cart;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.cart.service.CartItemService;
import shop.tryit.repository.cart.CartItemJpaRepository;

@Transactional
@SpringBootTest
class CartItemServiceTests {

    @Autowired
    CartItemJpaRepository cartItemJpaRepository;

    @Autowired
    CartItemService sut;

    @Test
    void 장바구니_상품_등록() {
        // given
        CartItem cartItem = CartItem.builder().build();

        // when
        Long savedId = sut.register(cartItem);

        // then
        assertTrue(cartItemJpaRepository.findById(savedId).isPresent());
    }

}