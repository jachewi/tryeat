package shop.tryit.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.item.dto.ItemSearchCondition;
import shop.tryit.domain.item.dto.ItemSearchDto;
import shop.tryit.domain.item.entity.Item;
import shop.tryit.domain.item.repository.ItemRepository;
import shop.tryit.domain.item.service.ItemService;

@Transactional
@SpringBootTest
class ItemServiceTests {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService sut;

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
    public void 상품_목록_조회() throws Exception {
        // given
        Item item1 = Item.builder().build();
        Item item2 = Item.builder().build();

        itemRepository.save(item1);
        itemRepository.save(item2);

        // when
        List<Item> items = sut.findItems();

        // then
        assertThat(items.size()).isEqualTo(2);
    }

    @Test
    public void 상품_삭제() throws Exception {
        // given
        Item item = Item.builder().build();

        Long savedId = itemRepository.save(item);

        // when, then
        assertThatCode(() ->
                sut.delete(savedId))
                .doesNotThrowAnyException();
    }

    @Test
    public void 상품_검색() throws Exception {
        // given
        for (int i = 0; i < 50; i++) {
            Item item = Item.builder()
                    .name("item" + i)
                    .build();
            itemRepository.save(item);
        }

        ItemSearchCondition condition = ItemSearchCondition.builder()
                .name("it")
                .build();

        PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        Page<ItemSearchDto> itemSearchDtoPage = sut.searchItem(condition, pageRequest);
        List<ItemSearchDto> contents = itemSearchDtoPage.getContent();

        // then
        Assertions.assertThat(contents.size()).isEqualTo(pageRequest.getPageSize());
    }

}
