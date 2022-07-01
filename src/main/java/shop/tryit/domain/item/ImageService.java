package shop.tryit.domain.item;

import static shop.tryit.domain.item.ImageType.MAIN;

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
public class ImageService {

    private final ItemService itemService;

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
