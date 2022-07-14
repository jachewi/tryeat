package shop.tryit.web.item;

import java.util.ArrayList;
import java.util.List;
import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemSearchDto;

public class ItemAdapter {

    public static Item toEntity(ItemFormDto form) {
        Item item = Item.builder()
                .name(form.getName())
                .price(form.getPrice())
                .category(form.getCategory())
                .stockQuantity(form.getStockQuantity())
                .build();

        return item;
    }

    public static ItemDto toDto(Item item, Image mainImage, Image detailImage) {
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .category(item.getCategory())
                .mainImage(mainImage)
                .detailImage(detailImage)
                .build();

        return itemDto;
    }

    public static List<ItemSearchDto> toDto(List<Item> items, List<Image> mainImages) {
        List<ItemSearchDto> itemSearchDtoList = new ArrayList<>();

        // 조회된 모든 Item -> ItemSearchDto 변환해서 리스트에 담음
        for (int i = 0; i < items.size(); i++) {
            ItemSearchDto dto = ItemSearchDto.builder()
                    .id(items.get(i).getId())
                    .name(items.get(i).getName())
                    .price(items.get(i).getPrice())
                    .mainImage(mainImages.get(i))
                    .build();

            itemSearchDtoList.add(dto);
        }

        return itemSearchDtoList;
    }

}
