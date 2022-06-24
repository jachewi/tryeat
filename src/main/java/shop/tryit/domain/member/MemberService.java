package shop.tryit.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.repository.member.MemberJpaRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository repository;
    private final PasswordEncoder passwordEncoder;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.builder().password(encodedPassword).build();
        return repository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        if (repository.existsByEmail(member.getEmail())) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}