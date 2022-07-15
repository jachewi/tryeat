package shop.tryit.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemRepository;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;
import shop.tryit.repository.order.OrderDetailJpaRepository;
import shop.tryit.repository.order.OrderJpaRepository;

@Transactional
@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService sut;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private OrderDetailJpaRepository orderDetailJpaRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 주문_저장() {
        //given
        Member member = Member.builder().build();
        Order order = Order.of(member, OrderStatus.ORDER);

        //when
        Long savedId = sut.register(order);

        //then
        assertTrue(orderJpaRepository.findById(savedId).isPresent());
    }

    @Test
    void 주문_상세_내역_저장() {
        //given
        Item item = Item.builder().stockQuantity(10).build();
        Member member = Member.builder().build();
        Order order = Order.of(member, OrderStatus.ORDER);
        OrderDetail orderDetail = OrderDetail.of(item, order, 5000, 2);

        //when
        Long savedId = sut.detailRegister(orderDetail);

        //then
        assertTrue(orderDetailJpaRepository.findById(savedId).isPresent());
        assertEquals("주문 후 재고는 주문수량만큼 줄어야 한다.", 8,
                item.getStockQuantity());
    }

    @Test
    void 주문_취소() {
        //given
        Item item = Item.builder().stockQuantity(8).build();
        Member member = Member.builder().build();
        Order order = Order.of(member, OrderStatus.ORDER);

        Long savedId = orderJpaRepository.save(order).getId();
        Order cancelOrder = sut.findOne(savedId);

        //when1 : 주문정보 취소
        cancelOrder.cancel(OrderStatus.CANCEL);

        //then1 : 주문 상태 확인
        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL,
                cancelOrder.getStatus());
    }

    @Test
    void 주문_취소_재고_체크() {
        //given
        Item item = Item.builder().stockQuantity(8).build();
        Member member = Member.builder().build();
        Order order = Order.of(member, OrderStatus.CANCEL);
        OrderDetail orderDetail = OrderDetail.of(item, order, 5000, 2);

        //when
        Long savedDetailId = orderDetailJpaRepository.save(orderDetail).getId();
        OrderDetail cancelOrderDetail = sut.detailFindOne(savedDetailId);
        cancelOrderDetail.cancel();

        //then2 : 주문수량 롤백확인
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10,
                item.getStockQuantity());
    }

    @Test
    void 주문_목록() {
        //given
        Item item = Item.builder().stockQuantity(10).build();
        itemRepository.save(item);

        Member member = Member.builder().build();
        memberRepository.save(member);

        Order order1 = Order.of(member, OrderStatus.ORDER);
        Order order2 = Order.of(member, OrderStatus.ORDER);

        orderJpaRepository.save(order1);
        orderJpaRepository.save(order2);

        OrderDetail orderDetail1 = OrderDetail.of(item, order1, 5000, 2);
        OrderDetail orderDetail2 = OrderDetail.of(item, order2, 5000, 2);

        sut.detailRegister(orderDetail1);
        sut.detailRegister(orderDetail2);

        //when
        List<OrderDetail> orderDetails = sut.findOrder();

        //then
        assertThat(orderDetails).hasSize(2);
    }

}