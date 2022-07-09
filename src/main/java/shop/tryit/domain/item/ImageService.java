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
        return imageStore.storeImageFile(form.getMainImage(), MAIN);
    }

    /**
     * 상세 이미지 업로드
     */
    public Image uploadDetailImage(ItemFormDto form) throws IOException {
        return imageStore.storeImageFile(form.getDetailImage(), DETAIL);
    }

    /**
     * 메인 이미지 조회
     */
    public Image getMainImage(Long id) {
        Item item = itemService.findOne(id); // 같은 트랜잭션 안에서 진행하기 위함

        Image mainImage = null;

        for (Image image : item.getImages())
            if (image.getType()==MAIN)
                mainImage = image;

        return mainImage;
    }

    /**
     * 상세 이미지 조회
     */
    public Image getDetailImage(Long id) {
        Item item = itemService.findOne(id); // 같은 트랜잭션 안에서 진행하기 위함

        Image detailImage = null;

        for (Image image : item.getImages())
            if (image.getType()==DETAIL)
                detailImage = image;

        return detailImage;
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

    /**
     * 이미지 정보 추가
     */
    @Transactional
    public Item addImage(Item item, ItemFormDto form) throws IOException {
        Image mainImage = uploadMainImage(form);
        Image detailImage = uploadDetailImage(form);

        item.addImage(mainImage);
        item.addImage(detailImage);

        return item;
    }

    /**
     * 메인 이미지 수정
     */
    @Transactional
    public Image updateMainImage(long id, ItemFormDto form) throws IOException {
        log.info("======== 메인 이미지 수정 시작 ========");

        Image findMainImage = getMainImage(id); // 기존 메인 이미지를 찾아옴

        log.info("old 메인이미지 = {} , {}", findMainImage.getOriginFileName(), findMainImage.getStoreFileName());

        // 새로 넘어온 이미지가 없을 경우 수정 사항 x
        if (!form.getMainImage().isEmpty()) {
            imageStore.deleteImageFile(findMainImage.getStoreFileName()); // 기존 메인 이미지 삭제
            Image newMainImage = uploadMainImage(form); // 새로운 메인 이미지 서버에 저장
            findMainImage.update(newMainImage); // 메인 이미지 정보 업데이트

            log.info("new 메인이미지 = {}, {}", newMainImage.getOriginFileName(), newMainImage.getStoreFileName());
            log.info("======== 메인 이미지 수정 종료 ========");

            return newMainImage;
        } else {
            log.info("변경 없음");
            log.info("======== 메인 이미지 수정 종료 ========");

            return findMainImage;
        }
    }

    /**
     * 상세 이미지 수정
     */
    @Transactional
    public Image updateDetailImage(long id, ItemFormDto form) throws IOException {
        log.info("======== 상세 이미지 수정 시작 ========");

        Image findDetailImage = getDetailImage(id); // 기존 상세 이미지를 찾아옴

        log.info("old 상세이미지 = {} , {}", findDetailImage.getOriginFileName(), findDetailImage.getStoreFileName());

        // 새로 넘어온 이미지가 있을 경우 기존에 있던 이미지 삭제 후 업로드
        if (!form.getDetailImage().isEmpty()) {
            imageStore.deleteImageFile(findDetailImage.getStoreFileName()); // 기존 상세 이미지 삭제
            Image newDetailImage = uploadDetailImage(form); // 새로운 상세 이미지 서버에 저장
            findDetailImage.update(newDetailImage); // 상세 이미지 정보 업데이트

            log.info("new 상세이미지 = {}, {}", newDetailImage.getOriginFileName(), newDetailImage.getStoreFileName());
            log.info("======== 상세 이미지 수정 종료 ========");

            return newDetailImage;
        } else {
            log.info("변경 없음");
            log.info("======== 상세 이미지 수정 종료 ========");

            return findDetailImage;
        }
    }

    /**
     * 이미지 삭제
     */
    public void deleteImage(Long id) throws IOException {
        Item item = itemService.findOne(id);

        for (Image image : item.getImages()) {
            imageStore.deleteImageFile(image.getStoreFileName());
        }
    }

}
