package shop.tryit.repository.question;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionRepository;

@DataJpaTest
class QuestionRepositoryImplTests {

    @Autowired
    private QuestionRepository sut;

    @Autowired
    private QuestionJpaRepository jpaRepository;

    @Test
    void 질문_저장_기능_테스트() {
        // given
        Member member = Member.builder().build();

        Question question = Question.of("title", "content", member);

        // when
        Long savedId = sut.save(question);

        // then
        assertTrue(jpaRepository.findById(savedId).isPresent());
    }

}