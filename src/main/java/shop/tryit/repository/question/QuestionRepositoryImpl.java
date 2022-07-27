package shop.tryit.repository.question;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionRepository;
import shop.tryit.domain.question.dto.QuestionSearchCondition;
import shop.tryit.domain.question.dto.QuestionSearchDto;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaRepository jpaRepository;

    @Override
    public Long save(Question question) {
        return jpaRepository.save(question).getId();
    }

    @Override
    public Optional<Question> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void delete(Question question) {
        jpaRepository.delete(question);
    }

    @Override
    public Page<QuestionSearchDto> searchQuestion(QuestionSearchCondition condition, Pageable pageable) {
        return jpaRepository.searchQuestion(condition, pageable);
    }

}
