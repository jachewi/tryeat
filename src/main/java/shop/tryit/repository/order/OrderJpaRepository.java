package shop.tryit.repository.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.order.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

//    List<Order> findAll(OrderSearch orderSearch);
}
