package shop.tryit.web.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.payment.PaymentService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String newPaymentForm(@ModelAttribute PaymentFormDto paymentFormDto) {
        log.info("======== 결제 폼 컨트롤러 시작 ========");
        log.info("결제할 상품 아이디 = {}", paymentFormDto.getItemId());
        log.info("결제할 상품 수량 = {}", paymentFormDto.getQuantity());

        return "/payment/payment-form";
    }

    @PostMapping("/kakao")
    public String newKakaopay(String number, String totalPrice) {
        log.info("결제 정보 {}", totalPrice);
        paymentService.register(PaymentAdapter.toEntity(number, totalPrice));
        return "redirect:/";
    }

}
