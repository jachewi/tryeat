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

}
