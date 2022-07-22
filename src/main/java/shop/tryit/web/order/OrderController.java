package shop.tryit.web.order;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemService;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.OrderDetail;
import shop.tryit.domain.order.OrderService;
import shop.tryit.domain.order.OrderStatus;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @PostMapping("/new")
    public String newOrder(@Valid @ModelAttribute OrderFormDto orderFormDto,
                           BindingResult bindingResult,
                           @AuthenticationPrincipal User user) {
        Member member = memberService.findMember(user.getUsername());

        //주문 정보 저장
        Order order = Order.of(member, OrderStatus.ORDER);
        orderService.register(order);

        Item item = itemService.findOne(orderFormDto.getItemId());
        orderFormDto.setItemName(item.getName());
        orderFormDto.setItemPrice(item.getPrice());

        //단건 주문인 경우
        OrderDetail orderDetail = OrderAdapter.toEntity(orderFormDto, item, order);
        orderService.detailRegister(orderDetail);

        return "redirect:/orders";
    }

    /**
     * 주문 목록
     */
    @GetMapping
    public String list(Model model) {
        List<OrderDetail> orders = orderService.findOrder();

        List<OrderDto> orderDto = OrderAdapter.toListDto(orders);

        model.addAttribute("orders", orderDto);

        return "/orders/list";
    }

    /**
     * 주문 취소를 위한 컨트롤러
     */
    @PostMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/orders/list";
    }

}
