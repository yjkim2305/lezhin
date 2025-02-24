package com.yjkim.lezhin.content.application.service;

import com.yjkim.lezhin.content.application.dto.ContentCreateCommand;
import com.yjkim.lezhin.content.application.dto.ContentResultDto;
import com.yjkim.lezhin.content.application.repository.ContentRepository;
import com.yjkim.lezhin.content.domain.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public void registerContent(ContentCreateCommand contentCreateCommand) {
        contentRepository.registerContent(Content.from(contentCreateCommand));
    }

    public ContentResultDto getContent(Long contentId, Long memberId, boolean isAdult) {
        Content content = contentRepository.getContent(contentId);
        content.validateAdultAccess(isAdult);

        //content 조회 이력 적재 필요

        return ContentResultDto.from(content);
    }
}
