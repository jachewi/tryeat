package shop.tryit.domain.payment;

import java.util.Optional;

public interface PaymentRepository {

    Long save(Payment payment);

    Optional<Payment> findByNum(Long Number);

}
