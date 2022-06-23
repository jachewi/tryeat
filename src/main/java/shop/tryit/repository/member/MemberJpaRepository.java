package shop.tryit.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.member.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
}
