package shop.tryit.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.item.entity.Image;
import shop.tryit.domain.item.entity.Item;
import shop.tryit.domain.item.service.ImageService;
import shop.tryit.domain.item.service.ItemService;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.payment.dto.PaymentDto;
import shop.tryit.domain.payment.dto.PaymentRequestDto;
import shop.tryit.domain.payment.dto.PaymentResponseDto;
import shop.tryit.domain.payment.dto.PaymentSaveDto;

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
    @Transactional(readOnly = true)
    public PaymentResponseDto paymentForm(PaymentRequestDto paymentRequestDto, User user) {
        Item item = itemService.findOne(paymentRequestDto.getItemId());
        Image mainImage = imageService.getMainImage(item.getId());
        Member member = memberService.findMember(user.getUsername());

        return toDto(item, mainImage, member, paymentRequestDto);
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

    public PaymentResponseDto toDto(Item item, Image mainImage, Member member, PaymentRequestDto paymentRequestDto) {
        return PaymentResponseDto.builder()
                .itemId(item.getId())
                .mainImage(mainImage)
                .itemName(item.getName())
                .quantity(paymentRequestDto.getQuantity())
                .totalPrice(item.getPrice() * paymentRequestDto.getQuantity())
                .memberName(member.getName())
                .memberPhone(member.getPhoneNumber())
                .zipcode(member.addressZipcode())
                .street_address(member.addressStreet())
                .jibeon_address(member.addressJibeon())
                .detail_address(member.addressDetail())
                .build();
    }

}
