package shop.tryit.domain.member;

public interface MemberRepository {

    Long save(Member member);

    boolean ExistsByEmail(String email);

}
