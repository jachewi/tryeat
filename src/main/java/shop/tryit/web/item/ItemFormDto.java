package shop.tryit.web.item;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import shop.tryit.domain.item.Category;

@Data
@NoArgsConstructor(access = PROTECTED)
public class ItemFormDto {

    private String name;

    private Integer price;

    private Integer stockQuantity;

    private Category category;

    private MultipartFile mainImage;

    private MultipartFile detailImage;

    @Builder
    private ItemFormDto(String name, Integer price, Integer stockQuantity, Category category, MultipartFile mainImage, MultipartFile detailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
    }

}
