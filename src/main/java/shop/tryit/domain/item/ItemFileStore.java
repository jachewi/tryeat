package shop.tryit.domain.item;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ItemFileStore {

    // TODO : 리팩토링 고려
    // @Value("${custom.path.upload-images}")
    // private String filePath;

    public String getFilePath() throws IOException {
        Path filePath = Paths.get(System.getProperty("user.home"), "item");

        if (Files.notExists(filePath)) {
            Files.createDirectories(filePath);
        }

        return filePath + "/";
    }

    public String getFullPath(String fileName) throws IOException {
        return getFilePath() + fileName;
    }

    public ItemFile storeItemFile(MultipartFile multipartFile, ItemFileType type) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originFileName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return ItemFile.of(originFileName, storeFileName, type);
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
