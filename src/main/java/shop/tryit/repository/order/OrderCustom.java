package shop.tryit.repository.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.tryit.domain.member.entity.Member;
import shop.tryit.domain.order.dto.OrderSearchDto;

public interface OrderCustom {

    Page<OrderSearchDto> searchOrders(Member member, Pageable pageable);

}
