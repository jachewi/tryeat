package shop.tryit.domain.cart.service;

import static java.util.Objects.isNull;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.repository.CartRepository;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;

    /**
     * 장바구니 생성
     */
    @Transactional
    public Long createCart(String email) {
        // 현재 로그인한 회원 엔티티 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("해당 회원을 찾을 수 없습니다."));

        // 회원의 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMember(member);

        // 회원에게 장바구니가 없을 경우, 장바구니 생성
        if (isNull(cart)) {
            cart = Cart.from(member);
            cartRepository.save(cart);
        }

        return cart.getId();
    }

}
