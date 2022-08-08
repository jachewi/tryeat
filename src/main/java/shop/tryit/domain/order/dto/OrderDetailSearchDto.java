package shop.tryit.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import shop.tryit.domain.item.dto.ItemSearchDto;

@Getter
public class OrderDetailSearchDto {

    private Long orderDetailId;

    private Long itemId;
    private ItemSearchDto itemSearchDto;
    private int quantity;

    @QueryProjection
    public OrderDetailSearchDto(Long orderDetailId, Long itemId, int quantity) {
        this.orderDetailId = orderDetailId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public void injectItemSearchDto(ItemSearchDto itemSearchDto) {
        this.itemSearchDto = itemSearchDto;
    }

}
