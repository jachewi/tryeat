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
    private Long zipcode;
    private String streetAddress;
    private String jibeonAddress;
    private String detailAddress;

    @QueryProjection
    public OrderSearchDto(Long orderId,
                          String orderNumber,
                          LocalDateTime createDate,
                          OrderStatus orderStatus,
                          Long zipcode,
                          String streetAddress,
                          String jibeonAddress,
                          String detailAddress) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.createDate = createDate;
        this.orderStatus = orderStatus;
        this.zipcode = zipcode;
        this.streetAddress = streetAddress;
        this.jibeonAddress = jibeonAddress;
        this.detailAddress = detailAddress;
    }

    public void injectOrderDetails(List<OrderDetailSearchDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
