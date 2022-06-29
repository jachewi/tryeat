package shop.tryit.domain.item;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    public void 상품_수정() throws Exception {
        // given
        Item item = Item.builder()
                .name("이름1")
                .build();

        Long savedId = itemRepository.save(item);

        Item newItem = Item.builder()
                .name("이름2")
                .build();

        // when
        Long updatedId = assertDoesNotThrow(() -> sut.update(savedId, newItem));

        // then
        assertNotNull(itemRepository.findById(updatedId));
    }

}