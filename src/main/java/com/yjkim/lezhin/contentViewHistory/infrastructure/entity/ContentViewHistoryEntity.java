package com.yjkim.lezhin.contentViewHistory.infrastructure.entity;

import com.yjkim.lezhin.common.domain.entity.BaseTimeEntity;
import com.yjkim.lezhin.contentViewHistory.domain.ContentViewHistory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content_view_history",
        indexes = {
                @Index(name = "idx_content_view_history_content_id", columnList = "contentId"),
                @Index(name = "idx_content_view_history_member_id", columnList = "memberId")
        })
public class ContentViewHistoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private Long memberId;

    @Builder
    private ContentViewHistoryEntity(Long id, Long contentId, Long memberId) {
        this.id = id;
        this.contentId = contentId;
        this.memberId = memberId;
    }

    public static ContentViewHistoryEntity toEntity(ContentViewHistory contentViewHistory) {
        return ContentViewHistoryEntity.builder()
                .id(contentViewHistory.getId())
                .contentId(contentViewHistory.getContentId())
                .memberId(contentViewHistory.getMemberId())
                .build();
    }
}
