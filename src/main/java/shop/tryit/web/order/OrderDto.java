package shop.tryit.web.order;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.tryit.domain.order.OrderStatus;

@Data
@NoArgsConstructor
public class OrderDto {
    /**
     * 주문 정보 DTO
     */
    private Long detailId;
    private String number;
    private String itemName;
    private LocalDateTime date;
    private OrderStatus status;

    @Builder
    private OrderDto(Long detailId, String number,
                     String itemName, LocalDateTime date,
                     OrderStatus status) {
        this.detailId = detailId;
        this.number = number;
        this.itemName = itemName;
        this.date = date;
        this.status = status;
    }

}
