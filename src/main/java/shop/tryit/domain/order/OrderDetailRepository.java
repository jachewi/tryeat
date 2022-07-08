package shop.tryit.domain.order;

import java.util.Optional;

public interface OrderDetailRepository {

    Long save(OrderDetail orderDetail);

    Optional<OrderDetail> findById(Long id);

}
