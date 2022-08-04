package shop.tryit.domain.payment.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long number;

    private String totalPrice;

    private Payment(Long number, String totalPrice) {
        this.number = number;
        this.totalPrice = totalPrice;
    }

    public static Payment of(Long number, String totalPrice) {
        return new Payment(number, totalPrice);
    }

}
