package shop.tryit.web.item;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.Image;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ItemListDto {

    private String name;

    private Integer price;

    private Image mainImage;

    @Builder
    private ItemListDto(String name, Integer price, Image mainImage) {
        this.name = name;
        this.price = price;
        this.mainImage = mainImage;
    }

}
