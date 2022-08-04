package shop.tryit.domain.member.repository;

import java.util.Optional;
import shop.tryit.domain.member.entity.Member;

public interface MemberRepository {

    Long save(Member member);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

}
