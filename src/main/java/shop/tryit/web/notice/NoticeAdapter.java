package shop.tryit.web.notice;

import shop.tryit.domain.member.Member;
import shop.tryit.domain.notice.Notice;

public class NoticeAdapter {

    private NoticeAdapter() {
    }

    public static Notice toEntity(NoticeSaveFormDto noticeSaveFormDto, Member member) {
        return Notice.builder()
                .member(member)
                .title(noticeSaveFormDto.getTitle())
                .content(noticeSaveFormDto.getContent())
                .build();
    }

    public static Notice toEntity(NoticeUpdateFormDto noticeUpdateFormDto, Member member) {
        return Notice.builder()
                .member(member)
                .title(noticeUpdateFormDto.getTitle())
                .content(noticeUpdateFormDto.getContent())
                .build();
    }

    public static NoticeViewFormDto toViewForm(Notice notice) {
        Member member = notice.getMember();
        return NoticeViewFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .memberEmail(member.getEmail())
                .build();
    }

    public static NoticeUpdateFormDto toUpdateForm(Notice notice) {
        Member member = notice.getMember();
        return NoticeUpdateFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .memberEmail(member.getEmail())
                .build();
    }

}