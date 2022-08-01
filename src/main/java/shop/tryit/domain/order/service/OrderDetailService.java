package shop.tryit.domain.order.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.OrderDetail;
import shop.tryit.domain.order.OrderDetailRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public Long save(OrderDetail orderDetail) {
        orderDetail.itemRemoveStock();
        return orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> findOrderDetailsByOrder(Order order) {
        return orderDetailRepository.findByOrder(order);
    }

}
