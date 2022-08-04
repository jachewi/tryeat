package shop.tryit.domain.payment.repository;

import java.util.Optional;
import shop.tryit.domain.payment.entity.Payment;

public interface PaymentRepository {

    Long save(Payment payment);

    Optional<Payment> findByNum(Long Number);

}
