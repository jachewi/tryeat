package shop.tryit.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final ImageStore imageStore;

    /**
     * 메인 이미지 url 조회
     */
    public String getMainImageUrl(String storeFileName) {
        return imageStore.getImageUrl(storeFileName);
    }

    /**
     * 상세 이미지 url 조회
     */
    public String getDetailImageUrl(String storeFileName) {
        return imageStore.getImageUrl(storeFileName);
    }

}
