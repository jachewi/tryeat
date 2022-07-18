package shop.tryit.domain.notice;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepository {

    Long save(Notice notice);

    Optional<Notice> findById(Long id);

    void delete(Notice notice);

    Page<Notice> searchNotices(Pageable pageable);
    
}
