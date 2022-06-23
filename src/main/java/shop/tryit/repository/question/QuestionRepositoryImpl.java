package shop.tryit.repository.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionRepository;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository jpaRepository;

    @Override
    public Long save(Question question) {
        return jpaRepository.save(question).getId();
    }

}
