package shop.tryit.domain.cart.repository;

import shop.tryit.domain.cart.entity.Cart;

public interface CartRepository {

    Long save(Cart cart);

}
