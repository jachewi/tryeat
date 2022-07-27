package shop.tryit.domain.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.answer.Answer;
import shop.tryit.domain.answer.AnswerRepository;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberRepository;
import shop.tryit.domain.question.dto.QuestionSearchCondition;
import shop.tryit.domain.question.dto.QuestionSearchDto;
import shop.tryit.domain.question.entity.Question;
import shop.tryit.domain.question.service.QuestionService;
import shop.tryit.repository.question.QuestionJpaRepository;

@Transactional
@SpringBootTest
class QuestionServiceTests {

    @Autowired
    private QuestionService sut;

    @Autowired
    private QuestionJpaRepository questionJpaRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void 저장_기능_테스트() {
        // given
        Member member = Member.builder().build();

        Question question = Question.of("title", "content", member);

        // when
        Long savedId = sut.register(question, " ");

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

    @Test
    void 삭제_기능_테스트() {
        // given
        Member member = Member.builder().build();
        Question question = Question.of("title", "content", member);
        Long savedId = questionJpaRepository.save(question).getId();

        // when, then
        assertThatCode(() ->
                sut.delete(savedId))
                .doesNotThrowAnyException();
    }

    @Test
    void 검색_서비스_테스트() {
        // given
        Member member = Member.builder()
                .email("abc@naver.com")
                .name("1")
                .password("123")
                .build();

        memberRepository.save(member);

        for (int i = 0; i < 50; i++) {
            Question question = Question.of("title" + i, "count", member);
            questionJpaRepository.save(question);

            for (int j = 0; j < i; j++) {
                Answer answer = Answer.of("content", question, member);
                answerRepository.save(answer);
            }
        }

        QuestionSearchCondition condition = QuestionSearchCondition.builder()
                .email("ab")
                .build();

        PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        Page<QuestionSearchDto> questionSearchDtos = sut.searchList(condition, pageRequest);
        List<QuestionSearchDto> content = questionSearchDtos.getContent();

        // then
        assertThat(content.size()).isEqualTo(pageRequest.getPageSize());
    }

}