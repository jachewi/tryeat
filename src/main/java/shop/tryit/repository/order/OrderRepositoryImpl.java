package shop.tryit.repository.order;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.order.OrderRepository;
import shop.tryit.domain.order.dto.OrderSearchDto;
import shop.tryit.domain.order.entity.Order;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    @Override
    public Long save(Order order) {
        log.info("save: order={}", order);
        return jpaRepository.save(order).getId();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Page<OrderSearchDto> searchOrders(Member member, Pageable pageable) {
        return jpaRepository.searchOrders(member, pageable);
    }

}
