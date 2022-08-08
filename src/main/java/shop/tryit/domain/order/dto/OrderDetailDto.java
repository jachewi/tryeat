package shop.tryit.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailDto {

    @JsonProperty("merchant_uid")
    private Long merchantUid;
    private Long itemId;
    private int orderQuantity;

}
