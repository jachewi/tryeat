package shop.tryit.domain.order;

import java.util.List;
import java.util.Optional;
import shop.tryit.domain.order.dto.OrderDetailSearchDto;
import shop.tryit.domain.order.entity.Order;

public interface OrderDetailRepository {

    Long save(OrderDetail orderDetail);

    Optional<OrderDetail> findById(Long id);

    List<OrderDetailSearchDto> searchOrderDetails(Order order);

    List<OrderDetail> findByOrder(Order order);

}
