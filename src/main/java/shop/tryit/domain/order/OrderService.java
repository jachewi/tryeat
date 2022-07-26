package shop.tryit.domain.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.item.ItemRepository;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 정보 저장
     */
    @Transactional
    public Long register(Order order) {
        return orderRepository.save(order);
    }

    /**
     * 주문 상세 저장
     */
    @Transactional
    public Long detailRegister(OrderDetail orderDetail) {
        orderDetail.itemRemoveStock();
        return orderDetailRepository.save(orderDetail);
    }

    /**
     * 주문 1 건 검색
     */
    public Order findOne(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 주문을 찾을 수 없습니다."));
    }

    public Page<OrderSearchDto> searchOrders(int page, String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        PageRequest pageRequest = PageRequest.of(page, 5);

        return orderRepository.searchOrders(member, pageRequest);
    }

    /**
     * 주문 목록
     */
    public List<OrderDetail> findOrder() {
        return orderDetailRepository.findAll();
    }

    /**
     * 주문 상세 조회
     */
    public OrderDetail detailFindOne(Long id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 주문 상품을 찾을 수 없습니다."));
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancel(Long orderId) {
        Order cancelOrder = findOne(orderId);
        cancelOrder.cancel(OrderStatus.CANCEL); // 주문 정보의 주문상태를 CANCEL 로 변경

        // 단일 상품 주문 취소
        OrderDetail cancelOrderDetail = detailFindOne(orderId);
        cancelOrderDetail.cancel();

        // TODO 장바구니 주문 후 다량주문 취소일 경우 삭제 로직 리팩토링 예정
//        //캔슬된 주문의 상세상품들 주문한 수량을 다시 돌려놓기
//        List<OrderDetail> orderDetails = findAll(orderId);
//        for (OrderDetail orderDetail : orderDetails) {
//            orderDetail.cancel();
//        }
    }

}
