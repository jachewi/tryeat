package shop.tryit.domain.order;

import java.util.Optional;

public interface OrderRepository {

    Long save(Order order);

    Optional<Order> findById(Long id);

}
