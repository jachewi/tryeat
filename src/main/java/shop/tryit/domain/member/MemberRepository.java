package shop.tryit.domain.member;

public interface MemberRepository {

    Long save(Member member);

    boolean existsByEmail(String email);

}
