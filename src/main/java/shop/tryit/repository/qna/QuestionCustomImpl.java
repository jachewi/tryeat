package shop.tryit.repository.qna;

import static java.util.stream.Collectors.toMap;
import static shop.tryit.domain.member.entity.QMember.member;
import static shop.tryit.domain.qna.entity.QAnswer.answer;
import static shop.tryit.domain.qna.entity.QQuestion.question;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import shop.tryit.domain.qna.dto.QQuestionSearchDto;
import shop.tryit.domain.qna.dto.QuestionSearchCondition;
import shop.tryit.domain.qna.dto.QuestionSearchDto;

@Repository
@RequiredArgsConstructor
public class QuestionCustomImpl implements QuestionCustom {

    private final JPAQueryFactory queryFactory;

    private static Map<Long, QuestionSearchDto> map;

    @Override
    public Page<QuestionSearchDto> searchQuestion(QuestionSearchCondition condition, Pageable pageable) {
        List<QuestionSearchDto> content = searchContent(condition, pageable);
        designateNumberOfAnswer(content);
        JPAQuery<Long> totalCount = getTotalCount(condition);
        return PageableExecutionUtils.getPage(content, pageable, totalCount::fetchOne);
    }

    private void designateNumberOfAnswer(List<QuestionSearchDto> content) {
        map = content.stream()
                .collect(toMap(QuestionSearchDto::getQuestionId, Function.identity()));

        answerCount(map.keySet())
                .forEach(this::registerNumberOfAnswer);
    }

    private void registerNumberOfAnswer(Tuple tuple) {
        Long questionId = tuple.get(question.id);
        QuestionSearchDto questionSearchDto = map.get(questionId);
        Long numberOfAnswers = Optional.ofNullable(tuple.get(answer.count())).orElse(0L);
        questionSearchDto.designateNumberOfAnswer(numberOfAnswers);
    }

    private JPAQuery<Long> getTotalCount(QuestionSearchCondition condition) {
        return queryFactory
                .select(question.count())
                .from(question)
                .where(containsCondition(condition.getCondition()));
    }

    private List<QuestionSearchDto> searchContent(QuestionSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(new QQuestionSearchDto(
                        question.id,
                        question.title,
                        member.email,
                        question.createDate))
                .from(question)
                .innerJoin(question.member, member)
                .where(containsCondition(condition.getCondition())
                )
                .orderBy(question.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<Tuple> answerCount(Collection<Long> questionIds) {
        return queryFactory
                .select(question.id, answer.count())
                .from(answer)
                .where(question.id.in(questionIds))
                .groupBy(question.id)
                .fetch();
    }

    private BooleanExpression containsCondition(String condition) {
        String nullSafeCondition = StringUtils.hasText(condition) ? condition:"";
        return questionTitleContains(nullSafeCondition).or(memberEmailContains(nullSafeCondition));
    }

    private BooleanExpression questionTitleContains(String condition) {
        return question.title.contains(condition);
    }

    private BooleanExpression memberEmailContains(String condition) {
        return question.member.email.contains(condition);
    }

}
