package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yjkim.lezhin.contentViewHistory.infrastructure.entity.QContentViewHistoryEntity.contentViewHistoryEntity;
import static com.yjkim.lezhin.member.infrastructure.entity.QMemberEntity.memberEntity;

@Repository
@RequiredArgsConstructor
public class CustomContentViewHistoryJpaRepositoryImpl implements CustomContentViewHistoryJpaRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<ContentViewResult> findContentViewHistories(Long contentId, Pageable pageable) {
        List<ContentViewResult> contentViewHistory = queryFactory
                .select(Projections.constructor(ContentViewResult.class,
                        memberEntity.memberName,
                        memberEntity.memberEmail,
                        contentViewHistoryEntity.createdDate))
                .from(contentViewHistoryEntity)
                .leftJoin(memberEntity).on(contentViewHistoryEntity.memberId.eq(memberEntity.id))
                .where(contentViewHistoryEntity.contentId.eq(contentId))
                .orderBy(contentViewHistoryEntity.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(contentViewHistoryEntity.count())
                .from(contentViewHistoryEntity)
                .where(contentViewHistoryEntity.contentId.eq(contentId));

        return PageableExecutionUtils.getPage(contentViewHistory, pageable, countQuery::fetchOne);
    }
}
