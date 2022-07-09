package shop.tryit.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.order.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
//    List<Order> findAll(OrderSearch orderSearch);
}
