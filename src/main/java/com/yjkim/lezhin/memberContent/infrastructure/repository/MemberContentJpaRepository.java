package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.yjkim.lezhin.memberContent.infrastructure.entity.MemberContentEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberContentJpaRepository extends JpaRepository<MemberContentEntity, Long>, CustomMemberContentJpaRepository {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM MemberContentEntity m WHERE m.memberId = :memberId AND m.contentId = :contentId AND m.episodeNumber = :episodeNumber")
    Optional<MemberContentEntity> findContentWithLock(Long memberId, Long contentId, int episodeNumber);
}
