package shop.tryit.web.item;

import static lombok.AccessLevel.PROTECTED;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.Image;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ItemSearchDto {

    private Long id;

    private String name;

    private Integer price;

    private Image mainImage;

    @Builder
    private ItemSearchDto(Long id, String name, Integer price, Image mainImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.mainImage = mainImage;
    }

    @QueryProjection
    public ItemSearchDto(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void injectMainImage(Image mainImage) {
        this.mainImage = mainImage;
    }

}
