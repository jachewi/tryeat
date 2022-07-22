package shop.tryit.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.OrderDetail;
import shop.tryit.domain.order.OrderDetailRepository;
import shop.tryit.domain.order.OrderRepository;
import shop.tryit.domain.order.OrderStatus;
import shop.tryit.repository.item.ItemJpaRepository;

@Transactional
@SpringBootTest
class ItemRepositoryTests {

    @Autowired
    ItemRepository sut;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    void 상품_저장() throws Exception {
        // given
        Item item = Item.builder().build();

        // when
        Long savedId = sut.save(item);

        // then
        assertThat(savedId).isEqualTo(item.getId());
    }

    @Test
    void 상품_한개_조회() throws Exception {
        // given
        Item item = Item.builder()
                .name("테스트")
                .build();

        // when
        sut.save(item);
        Item savedItem = sut.findById(item.getId()).get();

        // then
        assertThat(savedItem.getName()).isEqualTo("테스트");
    }

    @Test
    void 상품_전체_조회() throws Exception {
        // given
        Item itemA = Item.builder()
                .name("A")
                .build();
        Item itemB = Item.builder()
                .name("B")
                .build();

        sut.save(itemA);
        sut.save(itemB);

        // when
        List<Item> result = sut.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void 주문상품_PK값으로_상품_조회() {
        // given
        Item item = Item.builder()
                .name("A")
                .build();
        Long savedItemId = itemJpaRepository.save(item)
                .getId();

        Member member = Member.builder()
                .name("name")
                .email("email")
                .build();
        memberRepository.save(member);

        Order order = Order.of(member, OrderStatus.ORDER);
        orderRepository.save(order);

        OrderDetail orderDetail = OrderDetail.of(item, order, 1);
        Long savedOrderDetailId = orderDetailRepository.save(orderDetail);

        // when
        ItemSearchDto itemSearchDto = assertDoesNotThrow(() -> sut.findItemSearchDtoByOrderDetailId(savedOrderDetailId));

        // then
        assertThat(itemSearchDto.getId()).isEqualTo(savedItemId);
    }

}