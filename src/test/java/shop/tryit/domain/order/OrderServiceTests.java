package shop.tryit.domain.order;

import static org.springframework.test.util.AssertionErrors.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.item.entity.Item;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.order.service.OrderService;
import shop.tryit.repository.order.OrderJpaRepository;

@Transactional
@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService sut;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Test
    void 주문_취소() {
        // given
        Item item = Item.builder().stockQuantity(8).build();
        Member member = Member.builder().build();
        Order order = Order.of(member, OrderStatus.ORDER);

        Long savedId = orderJpaRepository.save(order).getId();
        Order cancelOrder = sut.findOne(savedId);

        // when1 : 주문정보 취소
        cancelOrder.cancel(OrderStatus.CANCEL);

        // then1 : 주문 상태 확인
        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL,
                cancelOrder.getStatus());
    }

}