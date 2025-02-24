package com.yjkim.lezhin.content.infrastructure.repository;

import com.yjkim.lezhin.content.infrastructure.entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentJpaRepository extends JpaRepository<ContentEntity, Long> {
}
