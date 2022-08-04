package shop.tryit.repository.answer;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.answer.entity.Answer;
import shop.tryit.domain.answer.repository.AnswerRepository;
import shop.tryit.domain.question.entity.Question;

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

    @Override
    public Page<Answer> findSearchByQuestion(Question question, Pageable pageable) {
        return jpaRepository.findSearchByQuestion(question, pageable);
    }

}
