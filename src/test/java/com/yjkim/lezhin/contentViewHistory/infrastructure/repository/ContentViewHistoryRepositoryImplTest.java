package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.yjkim.lezhin.common.config.QueryDslConfig;
import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.dto.TopContentViewResult;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import com.yjkim.lezhin.contentViewHistory.infrastructure.entity.ContentViewHistoryEntity;
import com.yjkim.lezhin.member.infrastructure.entity.MemberEntity;
import com.yjkim.lezhin.member.infrastructure.repository.MemberJpaRespository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({ContentViewHistoryRepositoryImpl.class, QueryDslConfig.class})
class ContentViewHistoryRepositoryImplTest {

    @Autowired
    private ContentViewHistoryJpaRepository contentViewHistoryJpaRepository;

    @Autowired
    private ContentViewHistoryRepositoryImpl contentViewHistoryRepository;

    @Autowired
    private MemberJpaRespository memberJpaRespository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("조회 이력 등록 테스트")
    void registerContentViewHistoryTest() {
        Long contentId = 1L;
        Long memberId = 100L;
        ContentViewHistory contentViewHistory = ContentViewHistory.of(contentId, memberId);

        contentViewHistoryRepository.registerContentViewHistory(contentViewHistory);

        Optional<ContentViewHistoryEntity> savedEntity = contentViewHistoryJpaRepository.findAll().stream().findFirst();

        savedEntity.ifPresent(contentViewHistoryEntity -> assertThat(contentViewHistoryEntity.getContentId()).isEqualTo(contentViewHistory.getContentId()));
    }

    @Test
    @DisplayName("특정 컨텐츠의 조회 이력 조회 테스트")
    void findContentViewHistoriesTest() {
        Long contentId = 1L;
        Long memberId = 100L;
        ContentViewHistoryEntity entity = ContentViewHistoryEntity.builder()
                .contentId(contentId)
                .memberId(memberId)
                .build();
        contentViewHistoryJpaRepository.save(entity);

        Optional<MemberEntity> member = memberJpaRespository.findById(memberId);

        Pageable pageable = PageRequest.of(0, 10);

        Page<ContentViewResult> result = contentViewHistoryRepository.findContentViewHistories(contentId, pageable);

        assertThat(result).isNotEmpty();
        member.ifPresent(memberEntity -> assertThat(result.getContent().get(0).memberName()).isEqualTo(memberEntity.getMemberName()));
    }

    @Test
    @DisplayName("사용자별 상위 10개 조회 이력 조회 테스트")
    void findTop10ContentViewHistoriesTest() {
        Long memberId = 100L;
        for (int i = 1; i <= 15; i++) {
            contentViewHistoryJpaRepository.save(ContentViewHistoryEntity.builder()
                    .contentId((long) i)
                    .memberId(memberId)
                    .build());
        }

        List<TopContentViewResult> result = contentViewHistoryRepository.findTop10ContentViewHistories(memberId);

        assertThat(result).hasSize(10);
    }

    @Test
    @DisplayName("조회 이력 삭제 테스트")
    void deleteContentViewHistoryTest() {
        Long contentId = 1L;
        Long memberId = 100L;
        ContentViewHistoryEntity entity = ContentViewHistoryEntity.builder()
                .contentId(contentId)
                .memberId(memberId)
                .build();
        contentViewHistoryJpaRepository.save(entity);

        contentViewHistoryRepository.deleteContentViewHistory(contentId);
        entityManager.flush();
        entityManager.clear();

        Optional<ContentViewHistoryEntity> deletedEntity = contentViewHistoryJpaRepository.findById(entity.getId());
        assertThat(deletedEntity).isEmpty();
    }
}