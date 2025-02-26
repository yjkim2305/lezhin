package com.yjkim.lezhin.contentViewHistory.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjkim.lezhin.common.config.QueryDslConfig;
import com.yjkim.lezhin.content.infrastructure.entity.ContentEntity;
import com.yjkim.lezhin.content.infrastructure.repository.ContentJpaRepository;
import com.yjkim.lezhin.contentViewHistory.application.dto.ContentViewResult;
import com.yjkim.lezhin.contentViewHistory.application.dto.TopContentViewResult;
import com.yjkim.lezhin.contentViewHistory.infrastructure.entity.ContentViewHistoryEntity;
import com.yjkim.lezhin.member.infrastructure.entity.MemberEntity;
import com.yjkim.lezhin.member.infrastructure.repository.MemberJpaRespository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({CustomContentViewHistoryJpaRepositoryImpl.class, QueryDslConfig.class})
class CustomContentViewHistoryJpaRepositoryImplTest {
    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private MemberJpaRespository memberJpaRespository;

    @Autowired
    private ContentJpaRepository contentJpaRepository;

    @Autowired
    private ContentViewHistoryJpaRepository contentViewHistoryJpaRepository;

    private CustomContentViewHistoryJpaRepositoryImpl customContentViewHistoryJpaRepository;

    @BeforeEach
    void setUp() {
        customContentViewHistoryJpaRepository = new CustomContentViewHistoryJpaRepositoryImpl(queryFactory);

        MemberEntity member = MemberEntity.builder()
                .memberName("테스트 유저")
                .memberEmail("test@example.com")
                .password("123456")
                .build();

        memberJpaRespository.save(member);

        ContentEntity content1 = ContentEntity.builder()
                .title("테스트 작품")
                .author("테스트 작가")
                .build();
        contentJpaRepository.save(content1);

        ContentEntity content2 = ContentEntity.builder()
                .title("테스트 작품2")
                .author("테스트 작가2")
                .build();
        contentJpaRepository.save(content2);

        ContentViewHistoryEntity history = ContentViewHistoryEntity.builder()
                .contentId(content1.getId())
                .memberId(member.getId())
                .build();

        contentViewHistoryJpaRepository.save(history);

        ContentViewHistoryEntity history2 = ContentViewHistoryEntity.builder()
                .contentId(content2.getId())
                .memberId(member.getId())
                .build();

        contentViewHistoryJpaRepository.save(history2);

        ContentViewHistoryEntity history3 = ContentViewHistoryEntity.builder()
                .contentId(content2.getId())
                .memberId(member.getId())
                .build();
        contentViewHistoryJpaRepository.save(history3);
    }

    @Test
    @DisplayName("특정 작품의 조회 이력을 페이징으로 가져오기")
    void findContentViewHistoriesTest() {
        ContentEntity savedContent = contentJpaRepository.findAll().get(0);
        Long contentId = savedContent.getId();
        Pageable pageable = PageRequest.of(0, 10);

        Page<ContentViewResult> result = customContentViewHistoryJpaRepository.findContentViewHistories(contentId, pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isGreaterThan(0);
        assertThat(result.getContent().get(0).memberName()).isEqualTo("테스트 유저");
    }

    @Test
    @DisplayName("사용자의 상위 10개 조회 이력 가져오기")
    void findTop10ContentViewHistoriesTest() {
        MemberEntity savedMember = memberJpaRespository.findAll().get(0);
        Long memberId = savedMember.getId();

        List<TopContentViewResult> result = customContentViewHistoryJpaRepository.findTop10ContentViewHistories(memberId);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).title()).isEqualTo("테스트 작품2");
        assertThat(result.get(0).author()).isEqualTo("테스트 작가2");
    }
}