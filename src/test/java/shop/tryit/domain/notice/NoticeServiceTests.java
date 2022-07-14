package shop.tryit.domain.notice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class NoticeServiceTests {

    @Autowired
    private NoticeService sut;

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void 저장된것은_단건_조회시_값을_제대로_가져온다() {
        // given
        Notice notice = Notice.builder()
                .title("title")
                .content("content")
                .build();

        Long savedId = noticeRepository.save(notice);

        // when
        sut.findById(savedId);
        Notice findNotice = assertDoesNotThrow(() -> sut.findById(savedId));

        // then
        assertThat(findNotice).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(notice);
    }

    @Test
    void 저장되지않는_것을_조회시_예외가_발생한다() {
        assertThatThrownBy(() -> sut.findById(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("해당하는 공지사항이 없습니다.");
    }

}