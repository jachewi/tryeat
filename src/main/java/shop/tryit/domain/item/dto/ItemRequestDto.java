package shop.tryit.domain.item.dto;

import static lombok.AccessLevel.PROTECTED;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import shop.tryit.domain.item.entity.Category;

/* 상품 등록, 수정 요청 시 전달 받음 */
@Data
@NoArgsConstructor(access = PROTECTED)
public class ItemRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    @Length(min = 0, max = 10, message = "이름은 최대 10글자입니다.")
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotNull(message = "재고를 입력해주세요.")
    private Integer stockQuantity;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    private MultipartFile mainImage;

    private MultipartFile detailImage;

    @Builder
    private ItemRequestDto(String name, Integer price, Integer stockQuantity, Category category, MultipartFile mainImage, MultipartFile detailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
    }

}
