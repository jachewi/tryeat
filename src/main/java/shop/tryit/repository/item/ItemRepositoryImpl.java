package shop.tryit.repository.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemRepository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemJpaRepository itemJpaRepository;

    @Override
    public Long save(Item item) {
        log.info("save: item = {}", item);
        return itemJpaRepository.save(item).getId();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemJpaRepository.findById(id);
    }

    @Override
    public List<Item> findAll() {
        return itemJpaRepository.findAll();
    }

}
