package com.yjkim.lezhin.member.infrastructure.repository;

import com.yjkim.lezhin.common.config.QueryDslConfig;
import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.member.api.exception.MemberErrorType;
import com.yjkim.lezhin.member.domain.Member;
import com.yjkim.lezhin.member.infrastructure.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import({MemberRepositoryImpl.class, QueryDslConfig.class})
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepositoryImpl memberRepository;

    @Autowired
    private MemberJpaRespository memberJpaRespository;

    @Test
    @DisplayName("회원 이메일로 존재 여부 확인")
    void existsByMemberEmailTest() {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberEmail("test@example.com")
                .password("password123")
                .memberName("테스트 유저")
                .build();
        memberJpaRespository.save(memberEntity);

        boolean exists = memberRepository.existsByMemberEmail("test@example.com");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("회원 가입 기능 테스트")
    void signUpMemberTest() {
        Member member = Member.builder()
                .memberEmail("newuser@example.com")
                .password("securePass")
                .memberName("새로운 회원")
                .build();

        memberRepository.signUpMember(member);

        boolean exists = memberRepository.existsByMemberEmail("newuser@example.com");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("이메일로 회원찾기")
    void findByMemberEmailTest() {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberEmail("newuser@example.com")
                .password("findPassword")
                .memberName("찾는 회원")
                .build();
        memberJpaRespository.save(memberEntity);

        Member foundMember = memberRepository.findByMemberEmail("newuser@example.com");

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getMemberEmail()).isEqualTo("newuser@example.com");
    }

    @Test
    @DisplayName("존재하지 않는 이메일 조회 시 예외 발생")
    void findByMemberEmailTest_Exception() {
        CoreException exception = assertThrows(CoreException.class, ()
                -> memberRepository.findByMemberEmail("nonexist@example.com")
        );

        assertThat(MemberErrorType.NOT_EXIST_USER_PASSWORD.getMessage()).isEqualTo(exception.getMessage());
        assertThat(MemberErrorType.NOT_EXIST_USER_PASSWORD.name()).isEqualTo(exception.getErrorType().getErrorCode());
    }
}