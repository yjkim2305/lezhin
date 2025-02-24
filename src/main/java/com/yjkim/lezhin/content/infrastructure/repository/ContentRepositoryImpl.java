package com.yjkim.lezhin.content.infrastructure.repository;

import com.yjkim.lezhin.content.application.repository.ContentRepository;
import com.yjkim.lezhin.content.domain.Content;
import com.yjkim.lezhin.content.infrastructure.entity.ContentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {
    private final ContentJpaRepository contentJpaRepository;

    @Override
    public void registerContent(Content content) {
        contentJpaRepository.save(ContentEntity.toEntity(content));
    }
}
