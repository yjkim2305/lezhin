package com.yjkim.lezhin.refresh.infrastructure.entity;


import com.yjkim.lezhin.common.domain.entity.BaseTimeEntity;
import com.yjkim.lezhin.refresh.domain.Refresh;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "refresh",
        indexes = {
                @Index(name = "idx_refresh_token", columnList = "refreshToken")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;
    private String refreshToken;
    private LocalDateTime expiration;

    @Builder
    private RefreshEntity(Long id, String memberId, String refreshToken, LocalDateTime expiration) {
        this.id = id;
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public static RefreshEntity toEntity(Refresh refresh) {
        return RefreshEntity.builder()
                .id(refresh.getId())
                .memberId(refresh.getMemberId())
                .refreshToken(refresh.getRefreshToken())
                .expiration(refresh.getExpiration())
                .build();
    }
}
