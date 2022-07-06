package shop.tryit.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

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
        String newEncodedPassword = passwordEncoder.encode(newMember.getPassword());
        newMember.Password(newEncodedPassword);
        findMember.update(newMember.getName(), newMember.getAddress(),
                newMember.getPhoneNumber(), newMember.getPassword());
        return findMember.getEmail();
    }

}