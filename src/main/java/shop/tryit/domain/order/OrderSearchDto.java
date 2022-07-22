package shop.tryit.domain.order;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderSearchDto {

    private Long orderId;
    private String orderNumber;
    private LocalDateTime createDate;
    private OrderStatus orderStatus;

    private List<OrderDetailSearchDto> orderDetails;

    @QueryProjection
    public OrderSearchDto(Long orderId,
                          String orderNumber,
                          LocalDateTime createDate,
                          OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.createDate = createDate;
        this.orderStatus = orderStatus;
    }

    public void injectOrderDetails(List<OrderDetailSearchDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
