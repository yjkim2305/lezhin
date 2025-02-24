package com.yjkim.lezhin.content.infrastructure.repository;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.content.api.exception.ContentErrorType;
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

    @Override
    public Content getContent(Long contentId) {
        return Content.from(contentJpaRepository.findById(contentId).orElseThrow(
                () -> new CoreException(ContentErrorType.NOT_EXIST_CONTENT)));
    }
}
