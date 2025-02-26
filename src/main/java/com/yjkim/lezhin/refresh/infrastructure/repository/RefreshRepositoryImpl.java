package com.yjkim.lezhin.refresh.infrastructure.repository;


import com.yjkim.lezhin.refresh.application.repository.RefreshRepository;
import com.yjkim.lezhin.refresh.domain.Refresh;
import com.yjkim.lezhin.refresh.infrastructure.entity.RefreshEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshRepositoryImpl implements RefreshRepository {

    private final RefreshJpaRepository refreshJpaRepository;

    @Override
    public Boolean existsByRefreshToken(String refreshToken) {
        return refreshJpaRepository.existsByRefreshToken(refreshToken);
    }

    @Override
    public void deleteByRefreshToken(String refreshToken) {
        refreshJpaRepository.deleteByRefreshToken(refreshToken);
    }

    @Override
    public void save(Refresh refresh) {
        refreshJpaRepository.save(RefreshEntity.toEntity(refresh));
    }
}
