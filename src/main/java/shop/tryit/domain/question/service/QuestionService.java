package shop.tryit.domain.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.answer.AnswerRepository;
import shop.tryit.domain.question.QuestionRepository;
import shop.tryit.domain.question.dto.QuestionSearchCondition;
import shop.tryit.domain.question.dto.QuestionSearchDto;
import shop.tryit.domain.question.entity.Question;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long register(Question question, String password) {
        passwordEncoding(question, password);
        return questionRepository.save(question);
    }

    public boolean checkPassword(String password, Question question) {
        return passwordEncoder.matches(password, question.getPassword());
    }

    public Question findOne(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 질문을 찾을 수 없습니다."));
    }

    @Transactional
    public Long update(Long id, Question newQuestion) {
        Question findQuestion = findOne(id);
        findQuestion.update(newQuestion.getTitle(), newQuestion.getContent());
        return findQuestion.getId();
    }

    @Transactional
    public void delete(Long id) {
        Question question = findOne(id);
        questionRepository.delete(question);
        answerRepository.findByQuestion(question)
                .forEach(answerRepository::delete);
    }

    public Page<QuestionSearchDto> searchList(QuestionSearchCondition condition, Pageable pageable) {
        return questionRepository.searchQuestion(condition, pageable);
    }

    private void passwordEncoding(Question question, String password) {
        String encodePassword = passwordEncoder.encode(password);
        question.allocatePassword(encodePassword);
    }

}
