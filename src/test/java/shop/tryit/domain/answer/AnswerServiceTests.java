package shop.tryit.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.question.Question;
import shop.tryit.repository.answer.AnswerJpaRepository;

@Transactional
@SpringBootTest
class AnswerServiceTests {

    @Autowired
    private AnswerService sut;

    @Autowired
    private AnswerJpaRepository answerJpaRepository;

    @Test
    void 저장_기능_테스트() {
        // given
        Member member = Member.builder().build();
        Question question = Question.of("title", "content", member);
        Answer answer = Answer.of("content", question, member);

        // when
        Long savedId = sut.register(answer);

        // then
        assertThat(answerJpaRepository.findById(savedId).isPresent())
                .isTrue();
    }

    @Test
    void 수정_기능_테스트() {
        //given
        Member member = Member.builder().build();
        Question question = Question.of("title", "content", member);
        Answer answer = Answer.of("content2", question, member);
        answerJpaRepository.save(answer);
        Answer newAnswer = Answer.of("content3", question, member);

        // when
        Long updatedId = assertDoesNotThrow(() -> sut.update(answer.getId(), newAnswer));

        // then
        assertNotNull(answerJpaRepository.findById(updatedId).orElse(null));
    }

}