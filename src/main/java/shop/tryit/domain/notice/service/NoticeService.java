package shop.tryit.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import shop.tryit.domain.notice.entity.Notice;
import shop.tryit.domain.notice.repository.NoticeRepository;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Long save(Notice notice) {
        return noticeRepository.save(notice);
    }

    public Notice findById(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당하는 공지사항이 없습니다."));
    }

    public void update(Long id, Notice newNotice) {
        Notice notice = findById(id);
        notice.update(newNotice.getTitle(), newNotice.getContent());
    }

    public void delete(Long id) {
        noticeRepository.delete(findById(id));
    }

    public String findMemberEmailById(Long noticeId) {
        return findById(noticeId).getUserEmail();
    }

    public Page<Notice> searchNotices(int page) {
        Sort sort = Sort.by(Direction.DESC, "createDate");
        PageRequest pageRequest = PageRequest.of(page, 5, sort);
        return noticeRepository.searchNotices(pageRequest);
    }

}
