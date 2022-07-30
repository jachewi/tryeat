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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.tryit.domain.cart.dto.CartItemDto;
import shop.tryit.domain.cart.dto.CartListDto;
import shop.tryit.domain.cart.service.CartFacade;

@Slf4j
@Controller
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartFacade cartFacade;

    /**
     * 장바구니에 상품 담기
     */
    @PostMapping
    public @ResponseBody ResponseEntity addCartItem(@Valid @ModelAttribute CartItemDto cartItemDto,
                                                    BindingResult bindingResult,
                                                    @AuthenticationPrincipal User user) {
        log.info("장바구니에 담을 상품의 id = {}", cartItemDto.getItemId());
        log.info("장바구니에 담을 상품의 수량 = {}", cartItemDto.getQuantity());

        // 상품 재고 검증
        if (Boolean.FALSE.equals(cartFacade.checkItemStock(cartItemDto))) {
            bindingResult.rejectValue("quantity", "StockError",
                    String.format("현재 남은 수량은 %d개 입니다.%n%d개 이하로 담아주세요.", cartFacade.getItemStock(cartItemDto), cartFacade.getItemStock(cartItemDto)));
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

        return "/carts/list";
    }

    /**
     * 장바구니에 담긴 상품 수량 변경
     */
    @PostMapping("/{cartItemId}/update")
    public @ResponseBody ResponseEntity<String> update(@PathVariable Long cartItemId, @RequestParam int newQuantity) {
        cartFacade.updateCartItemQuantity(cartItemId, newQuantity);
        return ResponseEntity.ok("cartItem quantity update success");
    }
