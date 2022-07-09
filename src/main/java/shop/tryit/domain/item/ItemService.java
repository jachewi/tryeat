package shop.tryit.domain.item;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 상품 수정
     */
    @Transactional
    public Long update(Long itemId, Item newItem) {
        Item findItem = findOne(itemId);
        findItem.update(newItem.getName(), newItem.getPrice(), newItem.getStockQuantity(), newItem.getCategory());
        return findItem.getId();
    }

    /**
     * 상품 목록
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    /**
     * 특정 상품 조회
     */
    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("해당 상품을 찾을 수 없습니다."));
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void delete(Long itemId) {
        Item item = findOne(itemId);
        itemRepository.delete(item);
    }

}
