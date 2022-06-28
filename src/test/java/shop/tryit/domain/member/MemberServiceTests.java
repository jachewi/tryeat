package shop.tryit.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.repository.member.MemberJpaRepository;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService sut;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void 회원가입_테스트() {
        Member member = Member.builder()
                .email("test1234@gmail.com")
                .name("test")
                .password("11111")
                .build();

        Long savedMember = sut.saveMember(member);

        assertThat(memberJpaRepository.findById(savedMember).isPresent()).isTrue();
    }

    @Test
    void 중복_회원_성공_테스트() {
        Member member1 = Member.builder()
                .email("test1234@gmail.com")
                .name("test")
                .password("11111")
                .build();

        Member member2 = Member.builder()
                .email("test1234@gmail.com")
                .name("test")
                .password("11111")
                .build();

        sut.saveMember(member1);

        assertThatThrownBy(() -> sut.saveMember(member2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 가입된 회원입니다.");
    }

}