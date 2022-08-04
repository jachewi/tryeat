package shop.tryit.domain.payment;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.payment.entity.Payment;
import shop.tryit.domain.payment.service.PaymentService;
import shop.tryit.repository.payment.PaymentJpaRepository;

@SpringBootTest
@Transactional
class PaymentServiceTests {

    @Autowired
    PaymentJpaRepository jpaRepository;

    @Autowired
    PaymentService sut;

    @Test
    void 결제_정보_저장() {
        //given
        Payment payment = Payment.of(560723464190L, "5000");

        //when
        Long sevedId = sut.register(payment);

        //then
        assertTrue(jpaRepository.findById(sevedId).isPresent());
    }

}