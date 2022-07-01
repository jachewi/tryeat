package shop.tryit.web.item;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.tryit.domain.item.ImageStore;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemFileController {

    private final ImageStore imageStore;

    @ResponseBody
    @GetMapping("/files/{filename}")
    public Resource viewMainImage(@PathVariable String filename) throws IOException {
        log.info("mainImage.getStoreFileName = {}", filename);
        return new UrlResource("file:" + imageStore.getFullPath(filename));
    }

}
