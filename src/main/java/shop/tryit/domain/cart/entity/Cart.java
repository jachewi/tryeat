package shop.tryit.domain.cart.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.common.BaseTimeEntity;
import shop.tryit.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Cart extends BaseTimeEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Cart(Member member) {
        this.member = member;
    }

    public static Cart from(Member member) {
        return new Cart(member);
    }

}
