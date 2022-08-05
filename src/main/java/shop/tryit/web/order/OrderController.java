package shop.tryit.web.order;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
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

    @ResponseBody
    @PostMapping("/new")
    public ResponseEntity<Object> newOrder(@RequestBody List<OrderDetailDto> OrderDetailDtos,
                                           @AuthenticationPrincipal User user) {
        Long orderId = orderFacade.register(user, OrderDetailDtos);

        URI location = UriComponentsBuilder.newInstance()
                .scheme("http")
                .path("localhost:8080/orders/{id}")
                .buildAndExpand(orderId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public String list(Model model, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal User user) {
        Page<OrderSearchDto> orders = orderFacade.searchOrders(page, user);
        Pages<OrderSearchDto> pages = Pages.of(orders, 4);

        model.addAttribute("orders", orders);
        model.addAttribute("pages", pages.getPages());

        return "/orders/list";
    }

    @GetMapping("/{orderId}")
    public String findOne(@PathVariable Long orderId, Model model) {
        log.info("order='{}'", orderFacade.findOne(orderId));
        model.addAttribute("order", orderFacade.findOne(orderId));
        return "orders/detail";
    }

}


