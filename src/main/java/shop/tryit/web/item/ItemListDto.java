package shop.tryit.web.item;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.ItemFile;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ItemListDto {

    private String name;

    private Integer price;

    private ItemFile mainImage;

    @Builder
    private ItemListDto(String name, Integer price, ItemFile mainImage) {
        this.name = name;
        this.price = price;
        this.mainImage = mainImage;
    }

}
