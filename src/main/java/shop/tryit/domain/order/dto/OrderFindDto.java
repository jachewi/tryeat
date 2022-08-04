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
    private String jibeonAddress;
    private String detailAddress;
    private List<OrderDetailFindDto> orderDetailFindDtos;

    @Builder
    public OrderFindDto(Long orderId,
                        String orderNumber,
                        LocalDateTime orderDateTime,
                        OrderStatus orderStatus,
                        Long zipcode, String streetAddress,
                        String jibeonAddress,
                        String detailAddress,
                        List<OrderDetailFindDto> orderDetailFindDtos) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.jibeonAddress = jibeonAddress;
        this.detailAddress = detailAddress;
        this.orderDetailFindDtos = orderDetailFindDtos;
    }

    @Getter
    public static class OrderDetailFindDto {

        private Long itemId;
        private String ItemName;
        private int orderQuantity;
        private Image itemMainImage;

        @Builder
        public OrderDetailFindDto(Long itemId, String itemName, int orderQuantity, Image itemMainImage) {
            this.itemId = itemId;
            ItemName = itemName;
            this.orderQuantity = orderQuantity;
            this.itemMainImage = itemMainImage;
        }

    }

}
