package shop.tryit.domain.item.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.item.dto.ItemDto;
import shop.tryit.domain.item.dto.ItemFormDto;
import shop.tryit.domain.item.dto.ItemSearchCondition;
import shop.tryit.domain.item.dto.ItemSearchDto;
import shop.tryit.domain.item.entity.Image;
import shop.tryit.domain.item.entity.Item;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemFacade {

    private final ItemService itemService;
    private final ImageService imageService;

    /**
     * 상품 등록
     */
    @Transactional
    public Long register(ItemFormDto itemFormDto) throws IOException {
        Image mainImage = imageService.uploadMainImage(itemFormDto); // 서버에 이미지 저장
        Image detailImage = imageService.uploadDetailImage(itemFormDto); // 서버에 이미지 저장

        Item item = toEntity(itemFormDto);
        item.addImage(mainImage, detailImage);

        return itemService.register(item);
    }

    /**
     * 상품 목록 조회
     */
    public Page<ItemSearchDto> findItems(ItemSearchCondition itemSearchCondition, PageRequest pageRequest) {
        return itemService.searchItem(itemSearchCondition, pageRequest);
    }

    /**
     * 특정 상품 조회
     */
    public ItemDto findItem(Long itemId) {
        Item item = itemService.findItem(itemId);
        return toDto(item, item.getMainImage(), item.getDetailImage());
    }

    /**
     * 상품 수정
     */
    @Transactional
    public Long update(Long itemId, ItemFormDto itemFormDto) throws IOException {
        Item findItem = itemService.findItem(itemId);
        Item newItem = toEntity(itemFormDto);

        itemService.update(findItem, newItem);
        imageService.updateMainImage(findItem, itemFormDto);
        imageService.updateDetailImage(findItem, itemFormDto);

        return itemId;
    }

    /**
     * 상품 삭제
     */
    @Transactional
    public void delete(Long itemId) throws IOException {
        imageService.deleteImage(itemId); // 상품 이미지 서버에서 삭제
        itemService.delete(itemId);
    }

    public Item toEntity(ItemFormDto form) {
        return Item.builder()
                .name(form.getName())
                .price(form.getPrice())
                .category(form.getCategory())
                .stockQuantity(form.getStockQuantity())
                .build();
    }

    public ItemDto toDto(Item item, Image mainImage, Image detailImage) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stockQuantity(item.getStockQuantity())
                .category(item.getCategory())
                .mainImage(mainImage)
                .detailImage(detailImage)
                .build();
    }

    public List<ItemSearchDto> toDto(List<Item> items, List<Image> mainImages) {
        List<ItemSearchDto> itemSearchDtos = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            ItemSearchDto dto = ItemSearchDto.builder()
                    .id(items.get(i).getId())
                    .name(items.get(i).getName())
                    .price(items.get(i).getPrice())
                    .mainImage(mainImages.get(i))
                    .build();

            itemSearchDtos.add(dto);
        }

        return itemSearchDtos;
    }

}
