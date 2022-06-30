package shop.tryit.web.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemFile;

public class ItemAdapter {

    public static Item toEntity(ItemFormDto form, ItemFile mainImage, ItemFile detailImage) throws IOException {
        List<ItemFile> itemImages = new ArrayList<>();

        Item item = Item.builder()
                .name(form.getName())
                .price(form.getPrice())
                .category(form.getCategory())
                .stockQuantity(form.getStockQuantity())
                .images(itemImages)
                .build();

        item.addImage(mainImage);
        item.addImage(detailImage);

        return item;
    }

    public static List<ItemListDto> toListDto(List<Item> items, List<ItemFile> mainImages) {
        List<ItemListDto> toListDtoResult = new ArrayList<>();

        // 조회된 모든 Item -> ItemListDto 변환해서 리스트에 담음
        for (int i = 0; i < items.size(); i++) {
            ItemListDto listDto = ItemListDto.builder()
                    .name(items.get(i).getName())
                    .price(items.get(i).getPrice())
                    .mainImage(mainImages.get(i))
                    .build();

            toListDtoResult.add(listDto);
        }

        return toListDtoResult;
    }

}
