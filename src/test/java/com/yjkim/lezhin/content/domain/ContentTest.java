package com.yjkim.lezhin.content.domain;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.content.api.exception.ContentErrorType;
import com.yjkim.lezhin.content.domain.enums.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentTest {

    @Test
    @DisplayName("성인 작품을 성인이 접근 할 수 있다.")
    void validateAdultAccess() {
        Content content = Content.builder()
                .contentType(ContentType.ADULT)
                .build();

        assertDoesNotThrow(() -> content.validateAdultAccess(true));
    }

    @Test
    @DisplayName("미성년자는 성인 작품을 접근 할 시 예외가 발생한다.")
    void validateAdultAccess_exception() {
        Content content = Content.builder()
                .contentType(ContentType.ADULT)
                .build();
        CoreException exception = assertThrows(CoreException.class, ()
                -> content.validateAdultAccess(false)
        );
        assertThat(ContentErrorType.FORBIDDEN_ADULT_CONTENT.getMessage()).isEqualTo(exception.getMessage());
        assertThat(ContentErrorType.FORBIDDEN_ADULT_CONTENT.name()).isEqualTo(exception.getErrorType().getErrorCode());
    }

    @Test
    @DisplayName("일반 작품은 미성년자와 성인이 접근할 수 있다.")
    void validateAdultAccess_generalContent() {
        Content content = Content.builder()
                .contentType(ContentType.GENERAL)
                .build();

        assertDoesNotThrow(() -> content.validateAdultAccess(false));
        assertDoesNotThrow(() -> content.validateAdultAccess(true));
    }

}