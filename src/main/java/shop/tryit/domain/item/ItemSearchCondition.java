package shop.tryit.domain.item;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemSearchCondition {

    private String name; // 상품 이름

    private Category category; // 상품 카테고리 [DOG,CAT]

    @Builder
    private ItemSearchCondition(String name, Category category) {
        this.name = name;
        this.category = category;
    }

}
