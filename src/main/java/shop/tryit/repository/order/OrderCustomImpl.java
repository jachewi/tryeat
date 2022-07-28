package shop.tryit.repository.order;

import static shop.tryit.domain.member.QMember.member;
import static shop.tryit.domain.order.QOrder.order;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shop.tryit.domain.member.Member;
import shop.tryit.domain.order.Order;
import shop.tryit.domain.order.OrderDetailRepository;
import shop.tryit.domain.order.dto.OrderDetailSearchDto;
import shop.tryit.domain.order.dto.OrderSearchDto;
import shop.tryit.domain.order.dto.QOrderSearchDto;

@RequiredArgsConstructor
public class OrderCustomImpl implements OrderCustom {

    private final JPAQueryFactory queryFactory;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public Page<OrderSearchDto> searchOrders(Member member, Pageable pageable) {
        return PageableExecutionUtils.getPage(orderSearchContents(member), pageable, totalCount(member)::fetchOne);
    }

    private List<OrderSearchDto> orderSearchContents(Member findMember) {
        QOrderSearchDto qOrderSearchDto =
                new QOrderSearchDto(order.id,
                        order.number,
                        order.createDate,
                        order.status,
                        member.address.zipcode,
                        member.address.street_address,
                        member.address.jibeon_address,
                        member.address.detail_address);

        List<OrderSearchDto> content = queryFactory
                .select(qOrderSearchDto)
                .from(order)
                .where(order.member.eq(findMember))
                .orderBy(order.createDate.desc())
                .join(order.member, member).fetchJoin()
                .fetch();

        content.forEach(this::injectOrderDetails);
        return content;
    }

    private void injectOrderDetails(OrderSearchDto orderSearchDto) {
        Long orderId = orderSearchDto.getOrderId();
        Order order = findById(orderId);

        List<OrderDetailSearchDto> orderDetailSearchDtos = orderDetailRepository.searchOrderDetails(order);
        orderSearchDto.injectOrderDetails(orderDetailSearchDtos);
    }

    public JPAQuery<Long> totalCount(Member member) {
        return queryFactory.select(order.count())
                .from(order)
                .where(order.member.eq(member));
    }

    private Order findById(Long orderId) {
        return queryFactory
                .select(order)
                .from(order)
                .where(order.id.eq(orderId))
                .fetchOne();
    }

}
