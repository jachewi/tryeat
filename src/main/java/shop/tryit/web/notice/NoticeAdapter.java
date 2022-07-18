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

    public static NoticeViewFormDto toViewForm(Notice notice, String memberEmail) {
        return NoticeViewFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .memberEmail(memberEmail)
                .build();
    }

    public static NoticeUpdateFormDto toUpdateForm(Notice notice, String memberEmail) {
        return NoticeUpdateFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .memberEmail(memberEmail)
                .build();
    }

    public static NoticeSearchFormDto toSearchForm(Notice notice, String memberEmail) {
        return NoticeSearchFormDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .memberEmail(memberEmail)
                .createDate(notice.getCreateDate())
                .build();
    }

}