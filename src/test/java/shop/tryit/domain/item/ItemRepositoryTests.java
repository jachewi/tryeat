package shop.tryit.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
class ItemRepositoryTests {

    @Autowired
    ItemRepository sut;

    @Test
    void 상품_저장() throws Exception {
        // given
        Item item = Item.builder().build();

        // when
        Long savedId = sut.save(item);

        // then
        assertThat(savedId).isEqualTo(item.getId());
    }

    @Test
    void 상품_한개_조회() throws Exception {
        // given
        Item item = Item.builder()
                        .name("테스트")
                        .build();

        // when
        sut.save(item);
        Item savedItem = sut.findById(item.getId()).get();

        // then
        assertThat(savedItem.getName()).isEqualTo("테스트");
    }


    @Test
    void 상품_전체_조회() throws Exception {
        // given
        Item itemA = Item.builder()
                         .name("A")
                         .build();
        Item itemB = Item.builder()
                         .name("B")
                         .build();

        sut.save(itemA);
        sut.save(itemB);

        // when
        List<Item> result = sut.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

}