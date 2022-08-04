package shop.tryit.domain.payment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.item.entity.Image;
import shop.tryit.domain.item.entity.Item;
import shop.tryit.domain.item.service.ItemService;
import shop.tryit.domain.member.entity.Member;
import shop.tryit.domain.member.service.MemberService;
import shop.tryit.domain.payment.dto.PaymentRequestDto;
import shop.tryit.domain.payment.dto.PaymentResponseDto;
import shop.tryit.domain.payment.dto.PaymentSaveDto;
import shop.tryit.domain.payment.entity.Payment;

@Slf4j
@Component
@RequiredArgsConstructor

public class PaymentFacade {

    private final PaymentService paymentService;
    private final ItemService itemService;
    private final MemberService memberService;

    /**
     * 결제 폼
     */
    @Transactional(readOnly = true)
    public List<PaymentResponseDto> paymentForm(List<PaymentRequestDto> paymentRequestDtoList) {

        List<Item> itemList = paymentRequestDtoList.stream()
                .map(paymentRequestDto -> paymentRequestDto.getItemId())
                .map(itemService::findItem)
                .collect(Collectors.toList());

        List<Image> mainImages = itemList.stream()
                .map(Item::getMainImage)
                .collect(Collectors.toList());

        return toProductDtoList(itemList, mainImages, paymentRequestDtoList);
    }

    /**
     * 장바구니 주문 -> 결제 폼(회원)
     */
    @Transactional(readOnly = true)
    public PaymentResponseDto paymentForm(User user) {
        Member member = memberService.findMember(user.getUsername());
        return toMemberDto(member);
    }

    /**
     * 결제 이력 저장
     */
    public Long register(PaymentSaveDto paymentSaveDto) {
        return paymentService.register(toEntity(paymentSaveDto));
    }

    /**
     * 장바구니에서 넘어온 상품 수량과 상품 재고 비교
     */
    public Boolean checkItemStock(List<PaymentRequestDto> paymentRequestDtoList) {
        List<Item> itemList = paymentRequestDtoList.stream()
                .map(paymentRequestDto -> paymentRequestDto.getItemId())
                .map(itemService::findItem)
                .collect(Collectors.toList());
        for (int i = 0; i < itemList.size(); i++) {
            if (Boolean.FALSE.equals(itemList.get(i).checkStock(paymentRequestDtoList.get(i).getQuantity()))) {
                log.info("-----------수량보다 많이 주문--------");
                return false;
            }
        }
        log.info("------------정상 주문 처리----------");
        return true;
    }

    private Payment toEntity(PaymentSaveDto paymentSaveDto) {
        return Payment.of(paymentSaveDto.getMerchant_uid(), paymentSaveDto.getAmount());
    }

    private List<PaymentResponseDto> toProductDtoList(List<Item> itemList, List<Image> mainImages, List<PaymentRequestDto> paymentRequestDtoList) {
        List<PaymentResponseDto> productDtoList = new ArrayList<>();
        for (int i = 0; i < paymentRequestDtoList.size(); i++) {
            PaymentResponseDto productDto = PaymentResponseDto.builder()
                    .itemId(itemList.get(i).getId())
                    .mainImage(mainImages.get(i))
                    .itemName(itemList.get(i).getName())
                    .quantity(paymentRequestDtoList.get(i).getQuantity())
                    .totalPrice(itemList.get(i).getPrice() * paymentRequestDtoList.get(i).getQuantity())
                    .build();
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    private PaymentResponseDto toMemberDto(Member member) {
        return PaymentResponseDto.builder()
                .memberName(member.getName())
                .memberPhone(member.getPhoneNumber())
                .zipcode(member.addressZipcode())
                .street_address(member.addressStreet())
                .jibeon_address(member.addressJibeon())
                .detail_address(member.addressDetail())
                .build();
    }

}
