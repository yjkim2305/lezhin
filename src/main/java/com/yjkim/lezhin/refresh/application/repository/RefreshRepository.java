package com.yjkim.lezhin.refresh.application.repository;


import com.yjkim.lezhin.refresh.domain.Refresh;

public interface RefreshRepository {
    Boolean existsByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    void save(Refresh refresh);
}
