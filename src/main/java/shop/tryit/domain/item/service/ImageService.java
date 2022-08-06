package shop.tryit.domain.item.service;

import static shop.tryit.domain.item.entity.ImageType.DETAIL;
import static shop.tryit.domain.item.entity.ImageType.MAIN;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.tryit.domain.item.dto.ItemRequestDto;
import shop.tryit.domain.item.entity.Image;
import shop.tryit.domain.item.entity.Item;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageStore imageStore;
    private final ItemService itemService;

    /**
     * 메인 이미지 s3 저장 및 엔티티 생성
     */
    public Image uploadMainImage(ItemRequestDto form) {
        return imageStore.uploadImageFile(form.getMainImage(), MAIN);
    }

    /**
     * 상세 이미지 s3 저장 및 엔티티 생성
     */
    public Image uploadDetailImage(ItemRequestDto form) {
        return imageStore.uploadImageFile(form.getDetailImage(), DETAIL);
    }

    /**
     * 메인 이미지 수정
     */
    public Image updateMainImage(Item findItem, ItemRequestDto form) throws IOException {
        log.info("======== 메인 이미지 수정 시작 ========");

        Image findMainImage = findItem.getMainImage(); // 기존 메인 이미지를 찾아옴

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
    public Image updateDetailImage(Item item, ItemRequestDto form) throws IOException {
        log.info("======== 상세 이미지 수정 시작 ========");

        Image findDetailImage = item.getDetailImage(); // 기존 상세 이미지를 찾아옴

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
    public void deleteImage(Long itemId) throws IOException {
        Item item = itemService.findItem(itemId);

        for (Image image : item.getImages()) {
            imageStore.deleteImageFile(image.getStoreFileName());
        }
    }

}
