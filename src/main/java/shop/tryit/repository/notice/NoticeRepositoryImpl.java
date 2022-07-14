package shop.tryit.repository.notice;

import lombok.RequiredArgsConstructor;
import shop.tryit.domain.notice.NoticeRepository;


@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepository {

    private final NoticeJpaRepository jpaRepository;

}
