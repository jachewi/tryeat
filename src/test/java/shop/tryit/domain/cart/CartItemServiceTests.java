package shop.tryit.domain.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.cart.service.CartItemService;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemRepository;
import shop.tryit.repository.cart.CartItemJpaRepository;

@Transactional
@SpringBootTest
public class CartItemServiceTests {
    @Autowired
    CartItemJpaRepository cartItemJpaRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CartItemService sut;

    private Item saveItem() {
        Item item = Item.builder().build();
        itemRepository.save(item);
        return item;
    }

    @Test
    void 장바구니에_상품이_없다면_상품_추가() {
        // given
        Item item = saveItem();

        CartItem cartItem = CartItem.builder().item(item).build();

        // when
        Long savedCartItemId = sut.addCartItem(cartItem); // 장바구니 상품 아이디

        // then
        assertNotNull(cartItemJpaRepository.findById(savedCartItemId));
    }

    @Test
    void 장바구니에_상품이_있다면_개수_증가() {
        // given
        Item item = saveItem();

        CartItem cartItem = CartItem.builder()
                .item(item)
                .count(2)
                .build();
        cartItemJpaRepository.save(cartItem);

        // when
        Long savedCartItemId = sut.addCartItem(cartItem);

        // then
        assertThat(cartItemJpaRepository.findById(savedCartItemId).get().getCount()).isEqualTo(4);
    }

}