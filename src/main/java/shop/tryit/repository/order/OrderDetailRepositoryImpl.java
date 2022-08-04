package shop.tryit.repository.order;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.order.OrderDetailRepository;
import shop.tryit.domain.order.dto.OrderDetailSearchDto;
import shop.tryit.domain.order.entity.Order;
import shop.tryit.domain.order.entity.OrderDetail;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderDetailRepositoryImpl implements OrderDetailRepository {

    private final OrderDetailJpaRepository detailJpaRepository;

    @Override
    public Long save(OrderDetail orderDetail) {
        log.info("save: order={}", orderDetail);
        return detailJpaRepository.save(orderDetail).getId();
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return detailJpaRepository.findById(id);
    }

    @Override
    public List<OrderDetailSearchDto> searchOrderDetails(Order order) {
        return detailJpaRepository.searchOrderDetails(order);
    }

    @Override
    public List<OrderDetail> findByOrder(Order order) {
        return detailJpaRepository.findByOrder(order);
    }

}
