package com.yjkim.lezhin.content.infrastructure.repository;

import com.yjkim.lezhin.common.config.QueryDslConfig;
import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.content.api.exception.ContentErrorType;
import com.yjkim.lezhin.content.domain.Content;
import com.yjkim.lezhin.content.infrastructure.entity.ContentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({ContentRepositoryImpl.class, QueryDslConfig.class})
class ContentRepositoryImplTest {

    @Autowired
    private ContentRepositoryImpl contentRepository;

    @Autowired
    private ContentJpaRepository contentJpaRepository;

    @Test
    @DisplayName("작품 등록 테스트")
    void registerContent_test() {
        Content content = Content.builder()
                .title("작품제목")
                .author("작품작가")
                .build();

        contentRepository.registerContent(content);

        Optional<ContentEntity> savedContent = contentJpaRepository.findAll().stream().findFirst();

        savedContent.ifPresent(contentEntity -> assertThat(contentEntity.getTitle()).isEqualTo(content.getTitle()));
    }

    @Test
    @DisplayName("작품 조회 테스트")
    void getContent_test() {
        ContentEntity contentEntity = ContentEntity.builder()
                .title("작품제목")
                .author("작품작가")
                .build();

        contentJpaRepository.save(contentEntity);
        Content content = contentRepository.getContent(contentEntity.getId());

        assertThat(content.getTitle()).isEqualTo(contentEntity.getTitle());
    }

    @Test
    @DisplayName("존재하지 않는 작품 조회 했을 경우 예외가 발생한다.")
    void getContent_Exception() {
        Long contentId = 100L;

        CoreException exception = assertThrows(CoreException.class,
                () -> contentRepository.getContent(contentId));

        assertThat(ContentErrorType.NOT_EXIST_CONTENT.getMessage()).isEqualTo(exception.getMessage());
        assertThat(ContentErrorType.NOT_EXIST_CONTENT.name()).isEqualTo(exception.getErrorType().getErrorCode());
    }

    @Test
    @DisplayName("작품 삭제 테스트")
    void deleteContentTest() {
        ContentEntity contentEntity = ContentEntity.builder()
                .title("삭제 테스트 작품")
                .build();

        contentJpaRepository.save(contentEntity);
        Long contentId = contentEntity.getId();

        contentRepository.deleteContent(contentId);
        
        Optional<ContentEntity> deletedContent = contentJpaRepository.findById(contentId);
        assertThat(deletedContent).isEmpty();
    }


}