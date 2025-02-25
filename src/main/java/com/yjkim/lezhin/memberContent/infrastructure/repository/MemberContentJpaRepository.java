package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.yjkim.lezhin.memberContent.infrastructure.entity.MemberContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberContentJpaRepository extends JpaRepository<MemberContentEntity, Long> {
}
