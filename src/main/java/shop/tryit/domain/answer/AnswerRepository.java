package shop.tryit.domain.answer;

import java.util.Optional;

public interface AnswerRepository {

    Long save(Answer answer);

    Optional<Answer> findById(Long id);

}
