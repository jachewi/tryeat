package shop.tryit.web.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.payment.PaymentFacade;
import shop.tryit.domain.payment.dto.PaymentRequsetDto;
import shop.tryit.domain.payment.dto.PaymentSaveDto;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @GetMapping
    public String newPaymentForm(Model model,
                                 @RequestBody @ModelAttribute PaymentRequsetDto paymentRequsetDto,
                                 @AuthenticationPrincipal User user) {
        log.info("======== 결제 폼 컨트롤러 시작 ========");
        log.info("결제할 상품 아이디 = {}", paymentRequsetDto.getItemId());
        log.info("결제할 상품 수량 = {}", paymentRequsetDto.getQuantity());

        model.addAttribute("payment", paymentFacade.paymentForm(paymentRequsetDto, user));

        return "/payment/payment-form";
    }

    @PostMapping("/kakao")
    public ResponseEntity newPaymentForm(@RequestBody @ModelAttribute PaymentSaveDto paymentSaveDto) {
        log.info("결제 정보 {}", paymentSaveDto);
        paymentFacade.register(paymentSaveDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
