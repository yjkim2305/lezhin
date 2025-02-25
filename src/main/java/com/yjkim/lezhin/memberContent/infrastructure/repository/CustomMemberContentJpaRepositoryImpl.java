package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yjkim.lezhin.content.infrastructure.entity.QContentEntity.contentEntity;
import static com.yjkim.lezhin.memberContent.infrastructure.entity.QMemberContentEntity.memberContentEntity;

@Repository
@RequiredArgsConstructor
public class CustomMemberContentJpaRepositoryImpl implements CustomMemberContentJpaRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<TopMemberContentResult> findTop10MemberContent(Long memberId) {
        return queryFactory
                .select(
                        Projections.constructor(TopMemberContentResult.class
                                , contentEntity.title
                                , contentEntity.author
                                , memberContentEntity.contentId.count()

                        ))
                .from(memberContentEntity)
                .leftJoin(contentEntity).on(memberContentEntity.contentId.eq(contentEntity.id))
                .where(memberContentEntity.memberId.eq(memberId))
                .groupBy(memberContentEntity.contentId, contentEntity.title, contentEntity.author)
                .orderBy(memberContentEntity.contentId.count().desc())
                .limit(10)
                .fetch();
    }
}
