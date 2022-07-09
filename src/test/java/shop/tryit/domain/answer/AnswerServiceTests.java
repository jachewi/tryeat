package shop.tryit.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;
import shop.tryit.domain.question.Question;
import shop.tryit.domain.question.QuestionRepository;
import shop.tryit.repository.answer.AnswerJpaRepository;

@Transactional
@SpringBootTest
class AnswerServiceTests {

    @Autowired
    private AnswerService sut;

    @Autowired
    private AnswerJpaRepository answerJpaRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QuestionRepository questionRepository;

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

    @Test
    void 삭제_기능_테스트() {
        // given
        Member member = Member.builder().build();
        memberRepository.save(member);

        Question question = Question.of("title", "content", member);
        questionRepository.save(question);

        Answer answer = Answer.of("content", question, member);
        Long savedId = answerJpaRepository.save(answer).getId();

        // when
        sut.delete(answer);

        // then
        assertNull(answerJpaRepository.findById(savedId).orElse(null));
    }

    @Test
    void 특정_질문에_대한_답변_검색_테스트() {
        // given
        Member member = Member.builder().build();
        memberRepository.save(member);

        Question question = Question.of("title", "content", member);
        questionRepository.save(question);

        int totalElements = 10;
        for (int i = 0; i < totalElements; i++) {
            Answer answer = Answer.of("content", question, member);
            Long savedId = answerJpaRepository.save(answer).getId();
        }

        // when
        int pageNumber = 3;
        Page<Answer> answersByQuestionId = sut.findAnswersByQuestionId(question.getId(), pageNumber);

        // then
        assertThat(answersByQuestionId.getTotalElements()).isEqualTo(totalElements);
    }

}