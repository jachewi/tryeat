package shop.tryit.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.ImageService;
import shop.tryit.domain.item.Item;
import shop.tryit.domain.item.ItemService;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.payment.dto.PaymentSaveDto;
import shop.tryit.web.payment.PaymentDto;
import shop.tryit.web.payment.PaymentRequsetDto;
import shop.tryit.web.payment.PaymentResponseDto;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;
    private final ItemService itemService;
    private final ImageService imageService;
    private final MemberService memberService;

    /**
     * 결제 폼
     */
    public PaymentResponseDto paymentForm(PaymentRequsetDto paymentRequsetDto, User user) {
        Item item = itemService.findOne(paymentRequsetDto.getItemId());
        Image mainImage = imageService.getMainImage(item.getId());
        Member member = memberService.findMember(user.getUsername());

        return toDto(item, mainImage, member, paymentRequsetDto);
    }

    /**
     * 결제 이력 저장
     */
    public Long register(PaymentSaveDto paymentSaveDto) {
        return paymentService.register(toEntity(paymentSaveDto));
    }

    public Payment toEntity(PaymentSaveDto paymentSaveDto) {
        return Payment.of(paymentSaveDto.getMerchant_uid(), paymentSaveDto.getAmount());
    }

    public PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                .number(payment.getNumber())
                .totalPrice(payment.getTotalPrice())
                .build();
    }

    public PaymentResponseDto toDto(Item item, Image mainImage, Member member, PaymentRequsetDto paymentRequsetDto) {
        return PaymentResponseDto.builder()
                .itemId(item.getId())
                .mainImage(mainImage)
                .itemName(item.getName())
                .quantity(paymentRequsetDto.getQuantity())
                .totalPrice(item.getPrice() * paymentRequsetDto.getQuantity())
                .memberName(member.getName())
                .memberPhone(member.getPhoneNumber())
                .zipcode(member.addressZipcode())
                .street_address(member.addressStreet())
                .jibeon_address(member.addressJibeon())
                .detail_address(member.addressDetail())
                .build();
    }

}