package shop.tryit.domain.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public Long save(Notice notice) {
        return noticeRepository.save(notice);
    }

    public Notice findById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당하는 공지사항이 없습니다."));
    }

    @Transactional
    public void update(Long id, Notice newNotice) {
        Notice notice = findById(id);
        notice.update(newNotice.getTitle(), newNotice.getContent());
    }

    @Transactional
    public void delete(Long id) {
        noticeRepository.delete(findById(id));
    }

    public String findMemberEmailById(Long noticeId) {
        return findById(noticeId).getUserEmail();
    }

}
