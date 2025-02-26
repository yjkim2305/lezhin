package com.yjkim.lezhin.refresh.domain;


import com.yjkim.lezhin.refresh.infrastructure.entity.RefreshEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Refresh {
    private Long id;
    private String memberId;
    private String refreshToken;
    private LocalDateTime expiration;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    private Refresh(Long id, String memberId, String refreshToken, LocalDateTime expiration, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static Refresh from(RefreshEntity refreshEntity) {
        return Refresh.builder()
                .id(refreshEntity.getId())
                .memberId(refreshEntity.getMemberId())
                .refreshToken(refreshEntity.getRefreshToken())
                .expiration(refreshEntity.getExpiration())
                .createdDate(refreshEntity.getCreatedDate())
                .updatedDate(refreshEntity.getUpdatedDate())
                .build();
    }

    public static Refresh of(String userId, String refreshToken, Long expiredMs) {
        return Refresh.builder()
                .memberId(userId)
                .refreshToken(refreshToken)
                .expiration(LocalDateTime.now().plus(Duration.ofMillis(expiredMs)))
                .build();
    }
}
