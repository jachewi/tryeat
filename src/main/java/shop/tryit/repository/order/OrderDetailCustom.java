package shop.tryit.repository.order;

import java.util.List;
import shop.tryit.domain.order.dto.OrderDetailSearchDto;
import shop.tryit.domain.order.entity.Order;

public interface OrderDetailCustom {

    List<OrderDetailSearchDto> searchOrderDetails(Order order);

}
