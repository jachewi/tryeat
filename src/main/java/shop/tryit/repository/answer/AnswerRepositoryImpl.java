package shop.tryit.repository.answer;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.answer.AnswerRepository;
import shop.tryit.domain.question.Question;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {

    private final AnswerJpaRepository jpaRepository;

    @Override
    public Long save(Answer answer) {
        return jpaRepository.save(answer).getId();
    }

    @Override
    public Optional<Answer> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Answer> findByQuestion(Question question) {
        return jpaRepository.findByQuestion(question);
    }

    @Override
    public void delete(Answer answer) {
        jpaRepository.delete(answer);
    }

}
