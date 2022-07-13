package shop.tryit.repository.item;

import static shop.tryit.domain.item.QImage.image;
import static shop.tryit.domain.item.QItem.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import shop.tryit.domain.item.Category;
import shop.tryit.domain.item.Image;
import shop.tryit.domain.item.ImageType;
import shop.tryit.domain.item.ItemSearchCondition;
import shop.tryit.web.item.ItemSearchDto;
import shop.tryit.web.item.QItemSearchDto;

@Repository
@RequiredArgsConstructor
public class ItemCustomImpl implements ItemCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ItemSearchDto> searchItems(ItemSearchCondition condition, Pageable pageable) {
        List<ItemSearchDto> itemSearchDtoList = searchContent(condition, pageable);
        injectMainImage(itemSearchDtoList);
        return PageableExecutionUtils.getPage(itemSearchDtoList, pageable, totalCount(condition)::fetchOne);
    }

    /**
     * 메인 이미지 주입
     */
    private void injectMainImage(List<ItemSearchDto> itemSearchDtoList) {
        Map<Long, ItemSearchDto> map = itemSearchDtoList.stream()
                .collect(Collectors.toMap(ItemSearchDto::getId, Function.identity()));

        searchMainImage(map.keySet())
                .forEach(image -> map.get(image.getItemId()).injectMainImage(image));
    }

    /**
     * 총 검색 결과 개수
     */
    private JPAQuery<Long> totalCount(ItemSearchCondition condition) {
        return queryFactory
                .select(item.count())
                .from(item)
                .where(
                        isContainName(condition.getName()),
                        isContainCategory(condition.getCategory())
                );
    }

    /**
     * 상품 검색 결과 (메인 이미지 제외)
     */
    private List<ItemSearchDto> searchContent(ItemSearchCondition condition, Pageable pageable) {
        QItemSearchDto qItemListDto = new QItemSearchDto(item.id, item.name, item.price);
        return queryFactory
                .select(qItemListDto)
                .from(item)
                .where(
                        isContainName(condition.getName()),
                        isContainCategory(condition.getCategory())
                )
                .orderBy(item.createDate.desc()) // 가장 최근 상품이 맨 앞으로 오도록 정렬
                .limit(pageable.getPageSize())   // 한 페이지에 보여줄 갯수
                .offset(pageable.getOffset())    // 어디서부터 보여줄 것인지
                .fetch();
    }

    /**
     * 상품 검색 결과 (메인 이미지)
     */
    private List<Image> searchMainImage(Collection<Long> itemIds) {
        return queryFactory.selectFrom(image)
                .where(
                        image.type.eq(ImageType.MAIN),
                        item.id.in(itemIds)
                )
                .join(image.item, item)
                .fetch();
    }

    /**
     * 상품 이름 검색 조건
     */
    private BooleanExpression isContainName(String name) {
        // 상품 이름으로 검색 할 경우 포함하지 않으면 null 반환
        return StringUtils.hasText(name) ? item.name.contains(name):null;
    }

    /**
     * 상품 카테고리 검색 조건
     */
    private BooleanExpression isContainCategory(Category category) {
        // 카테고리로 검색 할 경우 카테고리가 일치하지 않으면 null 반환
        return Optional.ofNullable(category).isPresent() ? item.category.eq(category):null;
    }

}