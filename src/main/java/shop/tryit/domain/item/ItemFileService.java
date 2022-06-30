package shop.tryit.domain.item;

import static shop.tryit.domain.item.ItemFileType.MAIN;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemFileService {

    private final ItemService itemService;

    /**
     * 메인 이미지 목록 조회
     */
    public List<ItemFile> findMainImages() {
        List<Item> items = itemService.findItems(); // 같은 트랜잭션 안에서 진행하기 위함

        List<ItemFile> mainImages = new ArrayList<>(); // 메인 이미지 파일만 담는 리스트

        for (Item item : items) {
            List<ItemFile> itemImages = item.getImages();

            for (ItemFile itemImage : itemImages) {
                if (itemImage.getType()==MAIN) {
                    mainImages.add(itemImage);
                }
            }
        }

        return mainImages;
    }

}
