package com.yjkim.lezhin.content.application.repository;

import com.yjkim.lezhin.content.domain.Content;

public interface ContentRepository {
    void registerContent(Content content);
    Content getContent(Long contentId);
    void deleteContent(Long contentId);
}
