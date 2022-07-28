package shop.tryit.repository.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.tryit.domain.notice.entity.Notice;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {

}
