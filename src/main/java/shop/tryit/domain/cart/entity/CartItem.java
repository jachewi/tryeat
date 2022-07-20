package shop.tryit.domain.cart.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.Item;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int count;

    @Builder
    private CartItem(Item item, Cart cart, int count) {
        this.item = item;
        this.cart = cart;
        this.count = count;
    }

    /**
     * 비즈니스 로직
     **/
    // 장바구니에 이미 상품이 있을 경우 개수 증가를 위한 로직
    public void addCount(int count) {
        this.count += count;
    }

}
