package shop.tryit.domain.order;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.order.dto.OrderSearchDto;
import shop.tryit.domain.order.entity.Order;

public interface OrderRepository {

    Long save(Order order);

    Optional<Order> findById(Long id);

    Page<OrderSearchDto> searchOrders(Member member, Pageable pageable);

}
