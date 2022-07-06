package shop.tryit.domain.order;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(unique = true)
    private int number;

    private LocalDateTime orderDate;  //주문 시간

    @OneToMany(mappedBy = "order", fetch = LAZY, cascade = {PERSIST, REMOVE})
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Enumerated(STRING) //enum 이름을 db에 저장
    private OrderStatus status;  //주문 상태[ORDER, CANCEL]

    @Builder
    private Order(Member member, int number, OrderStatus status) {
        this.member = member;
        this.number = number;
        this.status = status;
    }

}
