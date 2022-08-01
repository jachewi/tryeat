package shop.tryit.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.tryit.domain.item.ItemSearchCondition;
import shop.tryit.domain.item.dto.ItemSearchDto;

public interface ItemCustom {

    Page<ItemSearchDto> searchItems(ItemSearchCondition condition, Pageable pageable);

    ItemSearchDto findItemDtoById(Long orderDetailItemId);

}
