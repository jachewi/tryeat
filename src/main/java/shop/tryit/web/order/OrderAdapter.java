package shop.tryit.web.order;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.OrderDetail;

@NoArgsConstructor(access = PRIVATE)
public class OrderAdapter {

    public static OrderDetail toEntity(OrderFormDto orderFormDto, Item item, Order order, int orderTotalPrice) {

        return OrderDetail
                .of(item, order, orderTotalPrice, orderFormDto.getOrderQuantity());
    }

    public static OrderFormDto toDto(OrderDetail orderDetail) {

        return OrderFormDto.builder()
                .itemId(orderDetail.getItem().getId())
                .itemName(orderDetail.getItem().getName())
                .itemPrice(orderDetail.getOrderTotalPrice() / orderDetail.getCount())
                .orderQuantity(orderDetail.getCount())
                .build();
    }

    /**
     * 주문 정보 목록을 위한 DTO
     */
    public static List<OrderDto> toListDto(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderAdapter::toOrderDto)
                .collect(toList());
    }

    private static OrderDto toOrderDto(OrderDetail orderDetail) {
        return OrderDto.builder()
                .detailId(orderDetail.getId())
                .number(orderDetail.orderNumber())
                .itemName(orderDetail.itemName())
                .date(orderDetail.orderCreateDate())
                .status(orderDetail.orderStatus())
                .build();
    }

}
