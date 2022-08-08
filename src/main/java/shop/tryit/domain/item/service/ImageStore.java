package shop.tryit.domain.item.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shop.tryit.domain.item.entity.Image;
import shop.tryit.domain.item.entity.ImageType;

@Component
@RequiredArgsConstructor
public class ImageStore {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public String getFilePath() {
        Path filePath = Paths.get(System.getProperty("user.home"), "tryEat-img");

        try {
            if (Files.notExists(filePath)) {
                Files.createDirectories(filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath + "\\";
    }

    public String getFullPath(String fileName) {
        return getFilePath() + fileName;
    }

    /**
     * aws S3에 이미지 파일 저장 후 Image 엔티티를 생성해서 반환
     */
    public Image uploadImageFile(MultipartFile multipartFile, ImageType type) {
        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("파일이 없습니다.");
        }

        String originFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originFileName);

        storeS3(multipartFile, storeFileName);

        return Image.of(originFileName, storeFileName, type);
    }

    /**
     * aws S3에 이미지 파일 저장
     */
    private void storeS3(MultipartFile multipartFile, String storeFileName) {
        File file = new File(getFullPath(storeFileName)); // multipartFile -> File 변환

        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead)); // 누구나 S3에 업로드 된 파일에 접근이 가능하도록 권한 설정
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            if (file.exists()) {
                file.delete(); // 파일이 복사되어 저장된 임시파일을 제거
            }
        }
    }

    /**
     * aws S3에 저장된 이미지 파일 url 조회
     */
    public String getImageUrl(String storeFileName) {
        return amazonS3Client.getUrl(bucket, storeFileName).toString();
    }

    /**
     * aws S3에 저장된 이미지 파일 삭제
     */
    public void deleteS3(String storeFileName) {
        try {
            amazonS3Client.deleteObject(bucket, storeFileName);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    /**
     * storeFileName 생성
     * ex) image.png -> uuid.png
     */
    private String createStoreFileName(String originFileName) {
        String ext = extracted(originFileName); // 파일 확장자
        String uuid = UUID.randomUUID().toString(); // 서버에 저장될 이름
        return uuid + "." + ext;
    }

    /**
     * 파일 확장자 추출
     */
    private String extracted(String originFileName) {
        int pos = originFileName.lastIndexOf(".");
        return originFileName.substring(pos + 1);
    }

}
