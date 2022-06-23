package shop.tryit.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.repository.member.MemberJpaRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository repository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return repository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        boolean findMember = repository.existsByEmail(member.getEmail());
        if (findMember != false) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}