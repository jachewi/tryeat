package shop.tryit.domain.order;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository {

    Long save(OrderDetail orderDetail);

    Optional<OrderDetail> findById(Long id);

    List<OrderDetail> findAll();

    List<OrderDetailSearchDto> searchOrderDetails(Order order);

}
