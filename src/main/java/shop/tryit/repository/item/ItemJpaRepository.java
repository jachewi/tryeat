package shop.tryit.repository.item;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.item.entity.Item;

public interface ItemJpaRepository extends JpaRepository<Item, Long>, ItemCustom {

}
