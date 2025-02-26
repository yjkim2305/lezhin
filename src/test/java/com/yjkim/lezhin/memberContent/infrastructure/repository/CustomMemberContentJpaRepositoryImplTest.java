package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjkim.lezhin.common.config.QueryDslConfig;
import com.yjkim.lezhin.content.infrastructure.entity.ContentEntity;
import com.yjkim.lezhin.content.infrastructure.repository.ContentJpaRepository;
import com.yjkim.lezhin.member.infrastructure.entity.MemberEntity;
import com.yjkim.lezhin.member.infrastructure.repository.MemberJpaRespository;
import com.yjkim.lezhin.memberContent.application.dto.TopMemberContentResult;
import com.yjkim.lezhin.memberContent.infrastructure.entity.MemberContentEntity;
import org.junit.jupiter.api.BeforeEach;
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
@Import({CustomMemberContentJpaRepositoryImpl.class, QueryDslConfig.class})
class CustomMemberContentJpaRepositoryImplTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private MemberJpaRespository memberJpaRespository;

    @Autowired
    private ContentJpaRepository contentJpaRepository;

    @Autowired
    private MemberContentJpaRepository memberContentJpaRepository;

    private CustomMemberContentJpaRepositoryImpl customMemberContentJpaRepository;

    @BeforeEach
    void setUp() {
        customMemberContentJpaRepository = new CustomMemberContentJpaRepositoryImpl(queryFactory);

        MemberEntity member = MemberEntity.builder()
                .memberName("테스트 유저")
                .memberEmail("test@example.com")
                .password("123456")
                .build();

        memberJpaRespository.save(member);

        ContentEntity content1 = ContentEntity.builder()
                .title("테스트 작품")
                .author("테스트 작가")
                .totalEpisodes(11)
                .build();
        contentJpaRepository.save(content1);

        ContentEntity content2 = ContentEntity.builder()
                .title("테스트 작품2")
                .author("테스트 작가2")
                .totalEpisodes(10)
                .build();
        contentJpaRepository.save(content2);

        MemberContentEntity memberContent1 = MemberContentEntity.builder()
                .memberId(member.getId())
                .contentId(content1.getId())
                .episodeNumber(1)
                .build();

        memberContentJpaRepository.save(memberContent1);

        MemberContentEntity memberContent2 = MemberContentEntity.builder()
                .memberId(member.getId())
                .contentId(content2.getId())
                .episodeNumber(1)
                .build();

        memberContentJpaRepository.save(memberContent2);

        MemberContentEntity memberContent3 = MemberContentEntity.builder()
                .memberId(member.getId())
                .contentId(content2.getId())
                .episodeNumber(2)
                .build();

        memberContentJpaRepository.save(memberContent3);
    }


    @Test
    @DisplayName("사용자의 상위 10개 많이 구매한 작품 가져오기")
    void findTop10MemberContentTest() {
        MemberEntity savedMember = memberJpaRespository.findAll().get(0);
        Long memberId = savedMember.getId();

        List<TopMemberContentResult> result = customMemberContentJpaRepository.findTop10MemberContent(memberId);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).title()).isEqualTo("테스트 작품2");
        assertThat(result.get(0).author()).isEqualTo("테스트 작가2");
    }
}