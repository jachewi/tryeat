package shop.tryit.web.item;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.Category;
import shop.tryit.domain.item.Image;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ItemUpdateDto {

    private String name;

    private Integer price;

    private Integer stockQuantity;

    private Category category;

    private Image mainImage;

    private Image detailImage;

    @Builder
    private ItemUpdateDto(String name, Integer price, Integer stockQuantity, Category category, Image mainImage, Image detailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
    }

}
