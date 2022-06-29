package shop.tryit.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public Long register(Item item) {
        return itemRepository.save(item);
    }

    /**
     * 상품 목록
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

}
