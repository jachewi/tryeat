package shop.tryit.domain.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Long register(Answer answer) {
        return answerRepository.save(answer);
    }

}
