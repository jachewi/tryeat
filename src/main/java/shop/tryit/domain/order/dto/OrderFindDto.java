package shop.tryit.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.tryit.domain.item.entity.Image;
import shop.tryit.domain.order.entity.OrderStatus;

@Getter
public class OrderFindDto {

    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;
    private Long zipcode;
    private String streetAddress;
    private String detailAddress;
    private String memberName;
    private String memberPhoneNumber;
    private List<OrderDetailFindDto> orderDetailFindDtos;

    @Builder
    public OrderFindDto(Long orderId,
                        String orderNumber,
                        LocalDateTime orderDateTime,
                        OrderStatus orderStatus,
                        Long zipcode, String streetAddress,
                        String detailAddress,
                        String memberName,
                        String memberPhoneNumber,
                        List<OrderDetailFindDto> orderDetailFindDtos) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.memberName = memberName;
        this.memberPhoneNumber = memberPhoneNumber;
        this.orderDetailFindDtos = orderDetailFindDtos;
    }

    public Integer getOrderTotalPrice() {
        return orderDetailFindDtos.stream()
                .map(OrderDetailFindDto::totalPrice)
                .reduce((price1, price2) -> price1 + price2)
                .orElse(0);
    }

    @Getter
    public static class OrderDetailFindDto {

        private Long itemId;
        private String ItemName;
        private int orderQuantity;
        private Image itemMainImage;
        private int price;

        @Builder
        public OrderDetailFindDto(Long itemId, String itemName, int orderQuantity, Image itemMainImage, int price) {
            this.itemId = itemId;
            this.ItemName = itemName;
            this.orderQuantity = orderQuantity;
            this.itemMainImage = itemMainImage;
            this.price = price;
        }

        public int totalPrice() {
            return price * orderQuantity;
        }

    }

}
