package shop.tryit.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.order.OrderDetail;
import shop.tryit.domain.order.OrderDetailRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public Long save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

}
