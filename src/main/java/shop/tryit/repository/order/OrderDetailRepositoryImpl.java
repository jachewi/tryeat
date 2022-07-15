package shop.tryit.repository.order;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.order.OrderDetail;
import shop.tryit.domain.order.OrderDetailRepository;

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
    public List<OrderDetail> findAll() {
        return detailJpaRepository.findAll();
    }

}
