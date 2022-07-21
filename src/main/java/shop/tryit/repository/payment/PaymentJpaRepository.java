package shop.tryit.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.payment.Payment;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
