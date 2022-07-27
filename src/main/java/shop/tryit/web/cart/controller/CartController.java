package shop.tryit.web.cart.controller;

import static java.util.stream.Collectors.toList;

import java.security.Principal;
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
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.cart.service.CartItemService;
import shop.tryit.domain.cart.service.CartService;
import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.ImageService;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemService;
import shop.tryit.web.cart.dto.CartItemAdapter;
import shop.tryit.web.cart.dto.CartItemDto;
import shop.tryit.web.cart.dto.CartListDto;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ItemService itemService;
    private final ImageService imageService;

    /**
     * 장바구니에 상품 담기
     */
    @PostMapping
    public @ResponseBody ResponseEntity addCartItem(@Valid @ModelAttribute CartItemDto cartItemDto,
                                                    BindingResult bindingResult,
                                                    @AuthenticationPrincipal User user) {
        log.info("장바구니에 담을 상품의 id = {}", cartItemDto.getItemId());
        log.info("장바구니에 담을 상품의 수량 = {}", cartItemDto.getQuantity());

        Cart cart = cartService.findCart(user.getUsername());
        Item item = itemService.findOne(cartItemDto.getItemId());

        // 수량 검증
        if (item.getStockQuantity() < cartItemDto.getQuantity()) {
            bindingResult.rejectValue("quantity", "StockError", String.format("현재 남은 수량은 %d개 입니다.%n %d개 이하로 담아주세요.", item.getStockQuantity(), item.getStockQuantity()));
        }

        // 검증 실패시 400
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            for (FieldError error : bindingResult.getFieldErrors())
                sb.append(error.getDefaultMessage());

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        // 검증 성공시 장바구니에 상품 담기
        CartItem cartItem = CartItemAdapter.toEntity(cartItemDto, item, cart);
        Long cartItemId = cartItemService.addCartItem(cartItem);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    /**
     * 장바구니에 담긴 상품 조회
     */
    @GetMapping
    public String list(Model model, Principal principal) {
        Cart cart = cartService.findCart(principal.getName());

        List<CartItem> cartItems = cartItemService.findCartItemList(cart);

        List<Image> mainImages = cartItems.stream()
                .map(cartItem -> cartItem.getItemId()) // CartItem -> Long
                .map(imageService::getMainImage)// Long -> Image
                .collect(toList());

        List<CartListDto> cartListDtos = CartItemAdapter.toDto(cartItems, mainImages);

        // TODO: proxy 이슈 해결
        model.addAttribute("cartListDtos", cartListDtos);

        return "/cart/list";
    }

}
