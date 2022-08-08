package shop.tryit.web.item;

import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.common.Pages;
import shop.tryit.domain.item.dto.ItemRequestDto;
import shop.tryit.domain.item.dto.ItemResponseDto;
import shop.tryit.domain.item.dto.ItemSearchCondition;
import shop.tryit.domain.item.dto.ItemSearchDto;
import shop.tryit.domain.item.entity.Category;
import shop.tryit.domain.item.service.ItemFacade;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemFacade itemFacade;

    @GetMapping("/new")
    public String newItemForm(Model model) {
        Category[] categories = Category.values();

        model.addAttribute("itemRequestDto", ItemRequestDto.builder().build());
        model.addAttribute("categories", categories);

        return "items/register";

    }

    @PostMapping("/new")
    public String newItem(@Valid @ModelAttribute ItemRequestDto itemRequestDto, BindingResult bindingResult, Model model) throws IOException {
        // 이미지 검증 실패시
        if (itemRequestDto.getMainImage().isEmpty() || itemRequestDto.getDetailImage().isEmpty()) {
            bindingResult.rejectValue("mainImage", "ImageError", "메인이미지와 상세이미지는 필수값입니다.");
            bindingResult.rejectValue("detailImage", "ImageError", "메인이미지와 상세이미지는 필수값입니다.");
        }

        // 검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            Category[] categories = Category.values();
            model.addAttribute("categories", categories);
            
            return "items/register";

        }

        itemFacade.register(itemRequestDto);

        return "redirect:/items";
    }

    @GetMapping
    public String list(Model model,
                       @ModelAttribute ItemSearchCondition itemSearchCondition,
                       Pageable pageable) throws IOException {
        Category[] categories = Category.values();

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 12); // 한 페이지에 12개씩(4*3)
        Page<ItemSearchDto> itemSearchDtos = itemFacade.findItems(itemSearchCondition, pageRequest);
        Pages<ItemSearchDto> pages = Pages.of(itemSearchDtos, 4); // 페이지 버튼 4개씩

        model.addAttribute("categories", categories);
        model.addAttribute("itemSearchDtos", itemSearchDtos);
        model.addAttribute("pages", pages.getPages());

        log.info("상품 검색 조건 = 이름:{}, 카테고리:{}", itemSearchCondition.getName(), itemSearchCondition.getCategory());
        log.info("상품 개수 = {}", itemSearchDtos.getContent().size());

        return "items/list";

    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable long id, Model model) {
        Category[] categories = Category.values();
        model.addAttribute("categories", categories);

        ItemResponseDto itemResponseDto = itemFacade.findItem(id);
        model.addAttribute("item", itemResponseDto);

        return "items/update";

    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id,
                         @Valid @ModelAttribute("item") ItemRequestDto itemRequestDto,
                         BindingResult bindingResult,
                         Model model) throws IOException {
        // 검증 실패시 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            Category[] categories = Category.values();
            model.addAttribute("categories", categories);
            
            return "items/update";

        }

        itemFacade.update(id, itemRequestDto);

        return "redirect:/items";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {
        ItemResponseDto itemResponseDto = itemFacade.findItem(id);
        model.addAttribute("itemResponseDto", itemResponseDto);

        return "items/detail";

    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) throws IOException {
        itemFacade.delete(id);
        return "redirect:/items";
    }

}
