package shop.tryit.domain.order;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.item.entity.Item;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    private OrderDetail(Item item, Order order, int quantity) {
        this.item = item;
        this.order = order;
        this.quantity = quantity;
    }

    public static OrderDetail of(Item item, Order order, int count) {
        return new OrderDetail(item, order, count);
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        getItem().addStock(quantity);
    }

    public void itemRemoveStock() {
        getItem().removeStock(quantity);
    }

    /**
     * 주문 정보 목록
     */
    public LocalDateTime orderCreateDate() {
        return order.getCreateDate();
    }

    public String itemName() {
        return item.getName();
    }

    public String orderNumber() {
        return order.getNumber();
    }

    public OrderStatus orderStatus() {
        return order.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (o==null || getClass()!=o.getClass()) {
            return false;
        }
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
