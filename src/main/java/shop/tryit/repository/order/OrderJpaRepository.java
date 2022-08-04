package shop.tryit.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.order.entity.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long>, OrderCustom {

}
