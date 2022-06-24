package shop.tryit.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void 회원가입_테스트() {
        Member member = Member.builder()
                .email("test1234@gmail.com")
                .name("test")
                .password("11111")
                .build();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
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

        memberService.saveMember(member1);

        assertThatThrownBy(() -> memberService.saveMember(member2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 가입된 회원입니다.");
    }

}