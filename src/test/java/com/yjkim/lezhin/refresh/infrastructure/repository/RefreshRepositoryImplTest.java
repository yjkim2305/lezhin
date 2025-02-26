package com.yjkim.lezhin.refresh.infrastructure.repository;

import com.yjkim.lezhin.common.config.QueryDslConfig;
import com.yjkim.lezhin.refresh.domain.Refresh;
import com.yjkim.lezhin.refresh.infrastructure.entity.RefreshEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({RefreshRepositoryImpl.class, QueryDslConfig.class})
class RefreshRepositoryImplTest {

    @Autowired
    private RefreshJpaRepository refreshJpaRepository;

    @Autowired
    private RefreshRepositoryImpl refreshRepository;

    @Test
    @DisplayName("리프레시 토큰이 이미 존재하는지 확인하는 테스트")
    void existsByRefreshTokenTest() {
        RefreshEntity refreshToken = RefreshEntity.builder()
                .memberId("1")
                .refreshToken("refreshToken")
                .expiration(LocalDateTime.now())
                .build();

        refreshJpaRepository.save(refreshToken);

        boolean exists = refreshRepository.existsByRefreshToken(refreshToken.getRefreshToken());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("리프레시 토큰이 저장이 잘 되는지 확인하는 테스트")
    void testSaveRefreshToken() {
        String memberId = "1";
        String refreshToken = "refreshToken";
        Long expiredMs = 100L;
        Refresh refresh = Refresh.of(memberId, refreshToken, expiredMs);


        refreshRepository.save(refresh);
        Optional<RefreshEntity> savedEntity = refreshJpaRepository.findAll().stream().findFirst();

        savedEntity.ifPresent(refreshEntity -> assertThat(refreshEntity.getRefreshToken()).isEqualTo(refresh.getRefreshToken()));

    }

    @Test
    @DisplayName("리프레시 토큰이 정상적으로 삭제되는지 테스트")
    void testDeleteByRefreshToken() {
        String memberId = "1";
        String refreshToken = "refreshToken";
        Long expiredMs = 100L;

        RefreshEntity entity = RefreshEntity.toEntity(Refresh.of(memberId, refreshToken, expiredMs));
        refreshJpaRepository.save(entity);
        assertThat(refreshJpaRepository.existsByRefreshToken(refreshToken)).isTrue();

        refreshRepository.deleteByRefreshToken(refreshToken);

        assertThat(refreshJpaRepository.existsByRefreshToken(refreshToken)).isFalse();
    }
}