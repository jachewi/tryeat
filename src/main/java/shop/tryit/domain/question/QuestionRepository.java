package shop.tryit.domain.question;

import java.util.Optional;

public interface QuestionRepository {

    Long save(Question question);

    Optional<Question> findById(Long id);

    void delete(Question question);

}
