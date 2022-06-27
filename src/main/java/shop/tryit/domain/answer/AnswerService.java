package shop.tryit.domain.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Long register(Answer answer) {
        return answerRepository.save(answer);
    }

    private Answer findOne(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변을 찾을 수 없습니다."));
    }

}
