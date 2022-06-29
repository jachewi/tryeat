package shop.tryit.domain.member;

import java.util.Optional;

public interface MemberRepository {

    Long save(Member member);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

}
