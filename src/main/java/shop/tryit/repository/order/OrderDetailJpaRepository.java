package shop.tryit.repository.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.order.OrderDetail;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long> {
    Optional<OrderDetail> findById(Long id);

}
