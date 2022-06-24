package shop.tryit.domain.item;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ItemServiceTests {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemService sut;

    @Test
    public void 상품_등록() throws Exception {
        // given
        Item item = Item.builder().build();

        // when
        Long savedId = sut.register(item);

        // then
        assertTrue(itemRepository.findById(savedId).isPresent());
    }

}