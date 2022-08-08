package shop.tryit.web.item;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.tryit.domain.item.service.ImageStore;

//@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageStore imageStore;

    @ResponseBody
    @GetMapping("/files/{filename}")
    public Resource viewMainImage(@PathVariable String filename) throws IOException {
        return new UrlResource("file:" + imageStore.getFullPath(filename));
    }

}
