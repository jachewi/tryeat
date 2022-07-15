package shop.tryit.web.order;

import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
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
        List<OrderDto> orderDtoList = new ArrayList<>();

        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDto orderDto = OrderDto.builder()
                    .detailId(orderDetails.get(i).getId())
                    .number(orderDetails.get(i).getOrder().getNumber())
                    .itemName(orderDetails.get(i).getItem().getName())
                    .date(orderDetails.get(i).getOrder().getCreateDate())
                    .status(orderDetails.get(i).getOrder().getStatus())
                    .build();

            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

}
