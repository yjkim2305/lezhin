package com.yjkim.lezhin.memberContent.domain;

import com.yjkim.lezhin.content.domain.enums.PriceType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberContent {
    private Long id;
    private Long memberId;
    private Long contentId;
    private int episodeNumber;
    private PriceType priceType;  //사용자가 작품을 구매했을 때 무료, 유료인지 상태
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    private MemberContent(Long memberId, Long contentId, int episodeNumber, PriceType priceType) {
        this.memberId = memberId;
        this.contentId = contentId;
        this.episodeNumber = episodeNumber;
        this.priceType = priceType;
    }

    public static MemberContent of(Long memberId, Long contentId, int episodeNumber, PriceType priceType) {
        return MemberContent.builder()
                .memberId(memberId)
                .contentId(contentId)
                .episodeNumber(episodeNumber)
                .priceType(priceType)
                .build();
    }

}
