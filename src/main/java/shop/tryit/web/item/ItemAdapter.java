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

}
