package shop.tryit.repository.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.tryit.domain.item.ItemSearchCondition;
import shop.tryit.web.item.ItemListDto;

public interface ItemCustom {

    Page<ItemListDto> searchItem(ItemSearchCondition condition, Pageable pageable);

}
