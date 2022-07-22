package shop.tryit.repository.order;

import static shop.tryit.domain.order.QOrderDetail.orderDetail;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import shop.tryit.domain.item.ItemRepository;
import shop.tryit.domain.item.ItemSearchDto;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.OrderDetailSearchDto;
import shop.tryit.domain.order.QOrderDetailSearchDto;

@RequiredArgsConstructor
public class OrderDetailCustomImpl implements OrderDetailCustom {

    private final JPAQueryFactory queryFactory;
    private final ItemRepository itemRepository;

    @Override
    public List<OrderDetailSearchDto> searchOrderDetails(Order order) {
        QOrderDetailSearchDto qOrderDetailSearchDto = new QOrderDetailSearchDto(orderDetail.quantity, orderDetail.id);
        List<OrderDetailSearchDto> orderDetailSearchDtoList = queryFactory
                .select(qOrderDetailSearchDto)
                .from(orderDetail)
                .where(orderDetail.order.eq(order))
                .fetch();

        orderDetailSearchDtoList.forEach(this::injectItem);

        return orderDetailSearchDtoList;
    }

    private void injectItem(OrderDetailSearchDto orderDetailSearchDto) {
        Long orderDetailId = orderDetailSearchDto.getOrderDetailId();
        ItemSearchDto itemSearchDto = itemRepository.findItemSearchDtoByOrderDetailId(orderDetailId);
        orderDetailSearchDto.injectItemSearchDto(itemSearchDto);
    }

}
