package shop.tryit.Repository.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.member.Member;

@SpringBootTest
@Transactional
class MemberRepositoryImplTests {

    @Autowired
    private MemberRepositoryImpl sut;

    @Autowired
    private MemberJpaRepository jpaRepository;

    @Test
    void 회원가입() {
        //given
        Member member = Member.builder()
                .email("test1234@gmail.com")
                .build();

        //when
        Long savedId = sut.save(member);

        //then
        assertTrue(jpaRepository.findById(savedId).isPresent());
    }

    @Test
    void 회원존재여부_성공_테스트() {
        //given
        Member member = Member.builder()
                .email("test1234@gmail.com")
                .build();

        //when
        Member savedMember = jpaRepository.save(member);

        //then
        assertTrue(jpaRepository.existsByEmail(member.getEmail()));
    }

    @Test
    void 회원존재여부_실패_테스트() {
        //given
        Member member = Member.builder()
                .email("test5678@gmail.com")
                .build();

        //then
        assertFalse(jpaRepository.existsByEmail(member.getEmail()));
    }

}