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
import shop.tryit.domain.item.entity.Item;

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

    private int quantity;

    @Builder
    private CartItem(Item item, Cart cart, int quantity) {
        this.item = item;
        this.cart = cart;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return item.getId();
    }

    public String getItemName() {
        return item.getName();
    }

    public int getItemPrice() {
        return item.getPrice();
    }

    public int getItemStock() {
        return item.getStockQuantity();
    }

    /**
     * 비즈니스 로직
     **/
    // 장바구니에 이미 상품이 있을 경우 수량 증가를 위한 로직
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    // 장바구니 상품의 수량 변경을 위한 로직
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

}
