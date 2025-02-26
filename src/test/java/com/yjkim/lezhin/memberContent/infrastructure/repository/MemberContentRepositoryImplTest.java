package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.yjkim.lezhin.common.config.QueryDslConfig;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;
import com.yjkim.lezhin.memberContent.domain.MemberContent;
import com.yjkim.lezhin.memberContent.infrastructure.entity.MemberContentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({MemberContentRepositoryImpl.class, QueryDslConfig.class})
class MemberContentRepositoryImplTest {

    @Autowired
    private MemberContentJpaRepository memberContentJpaRepository;

    @Autowired
    private MemberContentRepositoryImpl memberContentRepository;

    @Test
    @DisplayName("사용자가 특정 에피소드를 구매한 적이 있는지 확인")
    void existMemberContent_True() {
        Long memberId = 1L;
        Long contentId = 100L;
        int episodeNumber = 5;
        PriceType priceType = PriceType.PAID;

        MemberContentEntity entity = MemberContentEntity.builder()
                .memberId(memberId)
                .contentId(contentId)
                .episodeNumber(episodeNumber)
                .priceType(priceType)
                .build();
        memberContentJpaRepository.save(entity);

        boolean exists = memberContentRepository.existMemberContent(memberId, contentId, episodeNumber);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("새로운 작품 구매 내역을 등록하면 데이터가 저장됨")
    void registerMemberContent_Success() {
        MemberContent memberContent = MemberContent.of(2L, 200L, 1, PriceType.FREE);

        memberContentRepository.registerMemberContent(memberContent);

        boolean exists = memberContentJpaRepository.findContentWithLock(2L, 200L, 1).isEmpty();
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("사용자가 가장 많이 본 작품 상위 10개를 조회한다.")
    void findTop10MemberContent_Success() {
        Long memberId = 1L;

        for (int i = 1; i <= 15; i++) {
            MemberContentEntity entity = MemberContentEntity.builder()
                    .memberId(memberId)
                    .contentId((long) i)
                    .episodeNumber(1)
                    .priceType(PriceType.PAID)
                    .build();
            memberContentJpaRepository.save(entity);
        }

        List<TopMemberContentResult> result = memberContentRepository.findTop10MemberContent(memberId);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(10);
    }
}