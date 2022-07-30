package shop.tryit.web.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.tryit.domain.common.Pages;
import shop.tryit.domain.order.dto.OrderDetailDto;
import shop.tryit.domain.order.dto.OrderSearchDto;
import shop.tryit.domain.order.service.OrderFacade;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping("/new")
    public ResponseEntity<String> newOrder(@RequestBody List<OrderDetailDto> orderFormDto,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal User user) {
        log.info("======== 결제 폼 컨트롤러 시작 ========");
        log.info("orderFormDto: {}", orderFormDto.toString());

        // TODO : 주문 등록 기능 구현
        return ResponseEntity.ok(orderFormDto.toString());
    }

    @GetMapping
    public String list(Model model, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal User user) {
        Page<OrderSearchDto> orders = orderFacade.searchOrders(page, user);
        Pages<OrderSearchDto> pages = Pages.of(orders, 4);

        model.addAttribute("orders", orders);
        model.addAttribute("pages", pages.getPages());

        return "/orders/list";
    }

    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        // TODO : 취소 로직 구현
        return "redirect:/orders/list";
    }

}


