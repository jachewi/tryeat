package shop.tryit.domain.notice;

import java.util.Optional;

public interface NoticeRepository {

    Long save(Notice notice);

    Optional<Notice> findById(Long id);

    void delete(Notice notice);


}
