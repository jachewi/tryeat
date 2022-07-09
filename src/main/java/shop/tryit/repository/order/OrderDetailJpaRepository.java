package shop.tryit.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.order.OrderDetail;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long> {

}
