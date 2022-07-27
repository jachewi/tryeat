package shop.tryit.web.cart;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.tryit.domain.cart.dto.CartItemDto;
import shop.tryit.domain.cart.dto.CartListDto;
import shop.tryit.domain.cart.service.CartFacade;
import shop.tryit.domain.item.entity.Item;
import shop.tryit.domain.item.service.ItemService;

@Slf4j
@Controller
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartFacade cartFacade;
    private final ItemService itemService;

    /**
     * 장바구니에 상품 담기
     */
    @PostMapping
    public @ResponseBody ResponseEntity addCartItem(@Valid @ModelAttribute CartItemDto cartItemDto,
                                                    BindingResult bindingResult,
                                                    @AuthenticationPrincipal User user) {
        log.info("장바구니에 담을 상품의 id = {}", cartItemDto.getItemId());
        log.info("장바구니에 담을 상품의 수량 = {}", cartItemDto.getQuantity());

        Item item = itemService.findOne(cartItemDto.getItemId());

        // TODO : if문 로직을 비즈니스 로직으로 분리
        // 수량 검증
        if (item.getStockQuantity() < cartItemDto.getQuantity()) {
            bindingResult.rejectValue("quantity", "StockError", String.format("현재 남은 수량은 %d개 입니다.%n%d개 이하로 담아주세요.", item.getStockQuantity(), item.getStockQuantity()));
        }

        // 검증 실패시 400
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            for (FieldError error : bindingResult.getFieldErrors())
                sb.append(error.getDefaultMessage());

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        // 검증 성공시 장바구니에 상품 담기
        Long cartItemId = cartFacade.addCartItem(cartItemDto, user);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    /**
     * 장바구니에 담긴 상품 조회
     */
    @GetMapping
    public String list(Model model, @AuthenticationPrincipal User user) {
        List<CartListDto> cartListDtos = cartFacade.findCartItems(user);

        model.addAttribute("cartListDtos", cartListDtos);

        return "/cart/list";
    }

}
