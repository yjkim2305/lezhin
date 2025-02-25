package com.yjkim.lezhin.content.application.service;

import com.yjkim.lezhin.common.event.ContentViewEvent;
import com.yjkim.lezhin.content.application.dto.ContentCreateCommand;
import com.yjkim.lezhin.content.application.dto.ContentResultDto;
import com.yjkim.lezhin.content.application.repository.ContentRepository;
import com.yjkim.lezhin.content.domain.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void registerContent(ContentCreateCommand contentCreateCommand) {
        contentRepository.registerContent(Content.from(contentCreateCommand));
    }

    //작품 조회 후 조회에 대한 이력 저장
    public ContentResultDto getContent(Long contentId, Long memberId, boolean isAdult) {
        Content content = contentRepository.getContent(contentId);
        content.validateAdultAccess(isAdult);

        //작품 조회 시 조회에 대한 이력 저장
        applicationEventPublisher.publishEvent(new ContentViewEvent(contentId, memberId));
        return ContentResultDto.from(content);
    }

    public void deleteContent(Long contentId) {
        contentRepository.deleteContent(contentId);
    }

    public void validateContent(Long contentId, boolean isAdult, int episodeNumber) {
        Content content = contentRepository.getContent(contentId);
        content.validateAdultAccess(isAdult);
        content.validateEpisode(episodeNumber);
    }
}
