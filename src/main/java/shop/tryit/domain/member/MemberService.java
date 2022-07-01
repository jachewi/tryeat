package shop.tryit.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public Long saveMember(Member member) {
        validateDuplicateMember(member);
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.Password(encodedPassword);
        return repository.save(member);
    }

    public Member findMember(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("해당 회원을 찾을 수 없습니다."));
    }

    private void validateDuplicateMember(Member member) {
        if (repository.existsByEmail(member.getEmail())) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Transactional
    public String update(String email, Member newMember) {
        Member findMember = findMember(email);
        findMember.update(newMember.getName(), newMember.getAddress(),
                newMember.getPhoneNumber(), newMember.getPassword());
        return findMember.getEmail();
    }

    public void kakaoLogin(String authorizedCode) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoMember userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + "admintoken";

        String kakaoEmail = email;

        // DB 에 중복된 email 이 있는지 확인
        Member kakaoUser = repository.findByEmail(email).orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            MemberRole role = MemberRole.USER;

            System.out.println("카카오 아이디: " + kakaoId);
            System.out.println("카카오 이메일: " + email);
            System.out.println("서버 아이디: " + kakaoEmail);
            System.out.println("서버 이름: " + username);
            System.out.println("서버 패스워드: " + encodedPassword);

            kakaoUser = Member.builder()
                    .email(kakaoEmail)
                    .password(encodedPassword)
                    .name(username)
                    .role(role)
                    .build();

            repository.save(kakaoUser);
        }else {
            System.out.println("기존 회원이므로 로그인을 시도");

            Member existingMember = findMember(email);
            Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(existingMember.getEmail(), existingMember.getPassword());
            Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        System.out.println("세이브 후 로그인 처리 시작");
        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(kakaoUser.getName(), kakaoUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}