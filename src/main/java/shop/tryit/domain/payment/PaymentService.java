package shop.tryit.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    /**
     * 결제 정보 저장
     */
    @Transactional
    public Long register(Payment payment) {
        return repository.save(payment);
    }

}
