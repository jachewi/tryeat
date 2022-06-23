package shop.tryit.domain.item;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.common.BaseTimeEntity;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id; // 상품 식별자

    private String name; // 상품 이름
    private int price; // 상품 가격
    private int stockQuantity; // 상품 재고

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private List<Category> categories; // 상품 카테고리 [DOG, CAT]

    private ItemImage mainImage; // 상품 메인이미지
    private List<ItemImage> detailImage; // 상품 상세이미지

    @Builder
    public Item(String name, int price, int stockQuantity, List<Category> categories, ItemImage mainImage, List<ItemImage> detailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categories = categories;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
    }

}
