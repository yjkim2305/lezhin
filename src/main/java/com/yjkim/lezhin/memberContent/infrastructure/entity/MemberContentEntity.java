package com.yjkim.lezhin.memberContent.infrastructure.entity;

import com.yjkim.lezhin.common.domain.entity.BaseTimeEntity;
import com.yjkim.lezhin.content.domain.enums.PriceType;
import com.yjkim.lezhin.memberContent.domain.MemberContent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Table(name = "member_content",indexes = {
        @Index(name = "idx_member_content_member_content_episode", columnList = "memberId, contentId, episodeNumber")
} )
public class MemberContentEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private int episodeNumber;

    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    @Builder
    private MemberContentEntity(Long id, Long memberId, Long contentId, PriceType priceType, int episodeNumber) {
        this.id = id;
        this.memberId = memberId;
        this.contentId = contentId;
        this.priceType = priceType;
        this.episodeNumber = episodeNumber;
    }

    public static MemberContentEntity toEntity(MemberContent memberContent) {
        return MemberContentEntity.builder()
                .id(memberContent.getId())
                .memberId(memberContent.getMemberId())
                .contentId(memberContent.getContentId())
                .episodeNumber(memberContent.getEpisodeNumber())
                .priceType(memberContent.getPriceType())
                .build();
    }
}
