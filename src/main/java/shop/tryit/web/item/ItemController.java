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
import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.ImageService;
import shop.tryit.domain.item.ItemService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ImageService imageService;

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

        Item item = ItemAdapter.toEntity(form, imageService.uploadMainImage(form), imageService.uploadDetailImage(form));
        itemService.register(item);

        return "redirect:/items";
    }

    @GetMapping
    public String list(Model model) throws IOException {
        List<Item> items = itemService.findItems();

        List<Image> mainImages = imageService.findMainImages();

        List<ItemListDto> itemListDto = ItemAdapter.toListDto(items, mainImages);

        model.addAttribute("items", itemListDto);

        return "/items/list";
    }


}
