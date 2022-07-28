package shop.tryit.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.member.MemberService;
import shop.tryit.domain.notice.dto.NoticeSaveFormDto;
import shop.tryit.domain.notice.dto.NoticeSearchFormDto;
import shop.tryit.domain.notice.dto.NoticeUpdateFormDto;
import shop.tryit.domain.notice.dto.NoticeViewFormDto;
import shop.tryit.domain.notice.entity.Notice;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeFacade {

    private final NoticeService noticeService;
    private final MemberService memberService;

    public Long register(User user, NoticeSaveFormDto noticeSaveFormDto) {
        String userEmail = user.getUsername();
        Member member = memberService.findMember(userEmail);
        Notice notice = toEntity(noticeSaveFormDto, member);
        return noticeService.save(notice);
    }

    public NoticeViewFormDto toViewForm(Long noticeId) {
        Notice notice = noticeService.findById(noticeId);
        String memberEmail = noticeService.findMemberEmailById(noticeId);
        return toViewForm(notice, memberEmail);
    }

    public NoticeUpdateFormDto toUpdateForm(Long noticeId) {
        Notice notice = noticeService.findById(noticeId);
        String memberEmail = noticeService.findMemberEmailById(noticeId);
        return toUpdateForm(notice, memberEmail);
    }

    public void update(User user, Long noticeId, NoticeUpdateFormDto noticeUpdateFormDto) {
        String userEmail = user.getUsername();
        Member member = memberService.findMember(userEmail);
        Notice newNotice = toEntity(noticeUpdateFormDto, member);
        noticeService.update(noticeId, newNotice);
    }

    public Page<NoticeSearchFormDto> searchNotices(int page) {
        return noticeService.searchNotices(page)
                .map(notice ->
                        toSearchForm(notice, noticeService.findMemberEmailById(notice.getId())));
    }

    public void delete(Long noticeId) {
        noticeService.delete(noticeId);
    }

    private Notice toEntity(NoticeSaveFormDto noticeSaveFormDto, Member member) {
        return Notice.builder()
                .member(member)
                .title(noticeSaveFormDto.getTitle())
                .content(noticeSaveFormDto.getContent())
                .build();
    }

    private Notice toEntity(NoticeUpdateFormDto noticeUpdateFormDto, Member member) {
        return Notice.builder()
                .member(member)
                .title(noticeUpdateFormDto.getTitle())
                .content(noticeUpdateFormDto.getContent())
                .build();
    }

    private NoticeViewFormDto toViewForm(Notice notice, String memberEmail) {
        return NoticeViewFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .memberEmail(memberEmail)
                .build();
    }

    private NoticeUpdateFormDto toUpdateForm(Notice notice, String memberEmail) {
        return NoticeUpdateFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .memberEmail(memberEmail)
                .build();
    }

    private NoticeSearchFormDto toSearchForm(Notice notice, String memberEmail) {
        return NoticeSearchFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .memberEmail(memberEmail)
                .createDate(notice.getCreateDate())
                .build();
    }

}
