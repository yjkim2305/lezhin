package com.yjkim.lezhin.memberContent.domain;

import com.yjkim.lezhin.content.domain.enums.PriceType;
import lombok.AccessLevel;
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

}
