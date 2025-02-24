package com.yjkim.lezhin.member.infrastructure.repository;

import com.yjkim.lezhin.member.infrastructure.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRespository extends JpaRepository<MemberEntity, Long> {
    Boolean existsByMemberEmail(String memberEmail);
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
