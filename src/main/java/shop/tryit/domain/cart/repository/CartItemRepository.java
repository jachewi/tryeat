package shop.tryit.domain.cart.repository;

import shop.tryit.domain.cart.entity.CartItem;

public interface CartItemRepository {

    Long save(CartItem cartItem);

}
