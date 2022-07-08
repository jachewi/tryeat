package shop.tryit.domain.order;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.common.BaseTimeEntity;
import shop.tryit.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(unique = true)
    @GeneratedValue(strategy = AUTO)
    private int number;

    @Enumerated(STRING) //enum 이름을 db에 저장
    private OrderStatus status;  //주문 상태[ORDER, CANCEL]

    private Order(Member member, OrderStatus status) {
        this.member = member;
        this.status = status;
    }

    public static Order of(Member member, OrderStatus status) {
        return new Order(member, status);
    }

}
