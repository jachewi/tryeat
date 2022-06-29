package shop.tryit.web.item;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.item.Category;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemFile;
import shop.tryit.domain.item.ItemFileStore;
import shop.tryit.domain.item.ItemService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemFileStore itemFileStore;

    @GetMapping("/new")
    public String newItemForm(Model model) {
        Category[] categories = Category.values();

        model.addAttribute("item", ItemFormDto.builder().build());
        model.addAttribute("categories", categories);

        return "/items/register";
    }

    @PostMapping("/new")
    public String newItem(@ModelAttribute("item") ItemFormDto form) throws IOException {
        log.info("saved item name = {}", form.getName());

        ItemFile mainImage = itemFileStore.storeMainImage(form.getMainImage());
        List<ItemFile> detailImages = itemFileStore.storeDetailImages(form.getDetailImage());

        // DB에 저장
        Item item = Item.builder()
                .name(form.getName())
                .category(form.getCategory())
                .stockQuantity(form.getStockQuantity())
                .price(form.getPrice())
                .mainImage(mainImage)
                .detailImage((detailImages))
                .build();

        itemService.register(item);

        // TODO: items.html 생성
        return "redirect:/";
    }

}
