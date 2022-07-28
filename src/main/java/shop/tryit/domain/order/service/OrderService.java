package shop.tryit.domain.order.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.OrderDetail;
import shop.tryit.domain.order.OrderDetailRepository;
import shop.tryit.domain.order.OrderRepository;
import shop.tryit.domain.order.dto.OrderSearchDto;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MemberRepository memberRepository;

    public Long register(Order order) {
        Long savedOrderId = orderRepository.save(order);
        Order findOrder = findOne(savedOrderId);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(findOrder);
        orderDetails.forEach(orderDetailRepository::save);

        return orderRepository.save(order);
    }

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

}
