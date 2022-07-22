package shop.tryit.domain.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import shop.tryit.domain.item.ItemSearchDto;

@Getter
public class OrderDetailSearchDto {

    private Long orderDetailId;

    private ItemSearchDto itemSearchDto;
    private int quantity;

    @QueryProjection
    public OrderDetailSearchDto(int quantity, Long orderDetailId) {
        this.quantity = quantity;
        this.orderDetailId = orderDetailId;
    }

    public void injectItemSearchDto(ItemSearchDto itemSearchDto) {
        this.itemSearchDto = itemSearchDto;
    }

}
