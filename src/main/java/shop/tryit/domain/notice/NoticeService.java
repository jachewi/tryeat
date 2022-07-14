package shop.tryit.domain.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Long save(Notice notice) {
        return noticeRepository.save(notice);
    }

}
