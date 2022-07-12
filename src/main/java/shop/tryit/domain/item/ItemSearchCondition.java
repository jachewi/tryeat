package shop.tryit.domain.item;

import lombok.Getter;

@Getter
public class ItemSearchCondition {

    private String name; // 상품 이름

    private Category category; // 상품 카테고리 [DOG,CAT]

}
