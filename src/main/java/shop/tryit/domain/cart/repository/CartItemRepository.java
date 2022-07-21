package shop.tryit.domain.cart.repository;

import shop.tryit.domain.cart.entity.Cart;
import shop.tryit.domain.cart.entity.CartItem;
import shop.tryit.domain.item.Item;

public interface CartItemRepository {

    Long save(CartItem cartItem);

    CartItem findByCartAndItem(Cart cart, Item item);

}
