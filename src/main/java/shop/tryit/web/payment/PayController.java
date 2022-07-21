package shop.tryit.web.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.payment.PaymentService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PayController {

    private final PaymentService paymentService;

    @GetMapping
    public String newKakapay() {
        log.info("--------------결제하기----------------");
        return "/payment/payment-form";
    }

    @PostMapping("/kakao")
    public String newKakaopay(String number, String totalPrice) {
        log.info("결제 정보 {}", totalPrice);
        paymentService.register(PaymentAdapter.toEntity(number, totalPrice));
        return "redirect:/";
    }

}
