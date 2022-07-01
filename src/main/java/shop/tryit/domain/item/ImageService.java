package shop.tryit.domain.item;

import static shop.tryit.domain.item.ImageType.DETAIL;
import static shop.tryit.domain.item.ImageType.MAIN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.web.item.ItemFormDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageStore imageStore;
    private final ItemService itemService;

    /**
     * 메인 이미지 업로드
     */
    public Image uploadMainImage(ItemFormDto form) throws IOException {
        return imageStore.storeItemFile(form.getMainImage(), MAIN);
    }

    /**
     * 상세 이미지 업로드
     */
    public Image uploadDetailImage(ItemFormDto form) throws IOException {
        return imageStore.storeItemFile(form.getDetailImage(), DETAIL);
    }

    /**
     * 메인 이미지 목록 조회
     */
    public List<Image> findMainImages() {
        List<Item> items = itemService.findItems(); // 같은 트랜잭션 안에서 진행하기 위함

        List<Image> mainImages = new ArrayList<>(); // 메인 이미지 파일만 담는 리스트

        for (Item item : items) {
            List<Image> images = item.getImages();

            for (Image image : images) {
                if (image.getType()==MAIN) {
                    mainImages.add(image);
                }
            }
        }

        return mainImages;
    }

}
