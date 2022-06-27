package shop.tryit.domain.question;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.member.Member;
import shop.tryit.repository.question.QuestionJpaRepository;

@Transactional
@SpringBootTest
class QuestionServiceTests {

    @Autowired
    private QuestionService sut;

    @Autowired
    private QuestionJpaRepository questionJpaRepository;

    @Test
    void 저장_기능_테스트() {
        // given
        Member member = Member.builder().build();

        Question question = Question.of("title", "content", member);

        // when
        Long savedId = sut.register(question);

        // then
        assertTrue(questionJpaRepository.findById(savedId).isPresent());
    }

    @Test
    void 게시판_수정_테스트() {
        // given
        Member member = Member.builder().build();
        Question question = Question.of("title", "content", member);
        Long savedId = questionJpaRepository.save(question).getId();
        Question newQuestion = Question.of("title2", "content2", member);

        // when
        Long updatedId = assertDoesNotThrow(() -> sut.update(savedId, newQuestion));

        // then
        assertNotNull(questionJpaRepository.findById(updatedId));
    }

}