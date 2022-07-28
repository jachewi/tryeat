package shop.tryit.repository.order;

import java.util.List;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.dto.OrderDetailSearchDto;

public interface OrderDetailCustom {

    List<OrderDetailSearchDto> searchOrderDetails(Order order);

}
