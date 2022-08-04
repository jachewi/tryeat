package shop.tryit.domain.item.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.tryit.domain.item.dto.ItemSearchCondition;
import shop.tryit.domain.item.dto.ItemSearchDto;
import shop.tryit.domain.item.entity.Item;

public interface ItemRepository {

    Long save(Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll();

    void delete(Item item);

    Page<ItemSearchDto> searchItems(ItemSearchCondition condition, Pageable pageable);

    ItemSearchDto findItemDtoById(Long itemId);

}
