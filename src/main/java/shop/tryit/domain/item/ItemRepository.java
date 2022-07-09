package shop.tryit.domain.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Long save(Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll();

    void delete(Item item);

}
