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
import org.springframework.web.bind.annotation.RequestMapping;
import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.ImageService;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemService;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.payment.PaymentService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final ItemService itemService;
    private final ImageService imageService;
    private final MemberService memberService;

    @GetMapping
    public String newPaymentForm(Model model,
                                 @ModelAttribute PaymentRequsetDto paymentRequsetDto,
                                 @AuthenticationPrincipal User user) {
        log.info("======== 결제 폼 컨트롤러 시작 ========");
        log.info("결제할 상품 아이디 = {}", paymentRequsetDto.getItemId());
        log.info("결제할 상품 수량 = {}", paymentRequsetDto.getQuantity());

        Item item = itemService.findOne(paymentRequsetDto.getItemId());
        Image mainImage = imageService.getMainImage(item.getId());
        Member member = memberService.findMember(user.getUsername());

        PaymentResponseDto responseDto = PaymentAdapter.toDto(item, mainImage, member, paymentRequsetDto);

        model.addAttribute("payment", responseDto);

        return "/payment/payment-form";
    }

    @PostMapping("/kakao")
    public ResponseEntity newPaymentForm(Long merchant_uid, String amount) {
        log.info("결제 정보 {}", amount);
        paymentService.register(PaymentAdapter.toEntity(merchant_uid, amount));

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
