package shop.tryit.domain.item.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import shop.tryit.domain.item.entity.Image;
import shop.tryit.domain.item.entity.ImageType;

@Component
public class ImageStore {

    // TODO : 리팩토링 고려
    // @Value("${custom.path.upload-images}")
    // private String filePath;

    public String getFilePath() throws IOException {
        Path filePath = Paths.get(System.getProperty("user.home"), "item");

        if (Files.notExists(filePath)) {
            Files.createDirectories(filePath);
        }

        return filePath + "\\";
    }

    public String getFullPath(String fileName) throws IOException {
        return getFilePath() + fileName;
    }

    public Image storeImageFile(MultipartFile multipartFile, ImageType type) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new IllegalStateException("파일이 없습니다.");
        }

        String originFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originFileName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return Image.of(originFileName, storeFileName, type);
    }

    /**
     * 이미지 파일 삭제
     */
    public void deleteImageFile(String storeFileName) throws IOException {
        Files.delete(Path.of(getFullPath(storeFileName)));
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
