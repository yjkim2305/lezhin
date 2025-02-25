package com.yjkim.lezhin.memberContent.infrastructure.repository;

import com.yjkim.lezhin.memberContent.application.repository.MemberContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberContentRepositoryImpl implements MemberContentRepository {
    private final MemberContentJpaRepository memberContentJpaRepository;


}
