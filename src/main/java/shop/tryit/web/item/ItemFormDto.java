package shop.tryit.web.item;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
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

    private List<MultipartFile> detailImage;

    @Builder
    private ItemFormDto(String name, Integer price, Integer stockQuantity, Category category, MultipartFile mainImage, List<MultipartFile> detailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
    }

}
