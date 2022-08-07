package shop.tryit.domain.item.dto;

import static lombok.AccessLevel.PROTECTED;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ItemSearchDto {

    private Long id;

    private String name;

    private Integer price;

    private String mainImageUrl;

    @Builder
    private ItemSearchDto(Long id, String name, Integer price, String mainImageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.mainImageUrl = mainImageUrl;
    }

    @QueryProjection
    public ItemSearchDto(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void injectMainImage(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

}
