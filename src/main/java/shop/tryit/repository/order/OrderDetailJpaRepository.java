package shop.tryit.repository.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.order.OrderDetail;
import shop.tryit.domain.order.entity.Order;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long>, OrderDetailCustom {

    List<OrderDetail> findByOrder(Order order);

}
