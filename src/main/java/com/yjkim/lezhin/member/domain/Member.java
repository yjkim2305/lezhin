package com.yjkim.lezhin.member.domain;

import com.yjkim.lezhin.common.exception.CoreException;
import com.yjkim.lezhin.member.api.exception.MemberErrorType;
import com.yjkim.lezhin.member.application.dto.MemberCreateCommand;
import com.yjkim.lezhin.member.infrastructure.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    private Long id;
    private String memberEmail;
    private String password;
    private String memberName;
    private LocalDate birthDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    @Builder
    private Member(Long id, String memberEmail, String password, String memberName, LocalDate birthDate, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.password = password;
        this.memberName = memberName;
        this.birthDate = birthDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void validatePassword(String password, BCryptPasswordEncoder bCryptPasswordEncoder) {
        boolean matches = bCryptPasswordEncoder.matches(password, this.password);
        if (!matches) {
            throw new CoreException(MemberErrorType.NOT_EXIST_USER_PASSWORD);
        }
    }

    public boolean isAdult() {
        return Period.between(this.birthDate, LocalDate.now()).getYears() >= 19;
    }

    public static Member createWithEncodedPassword(MemberCreateCommand memberCreateCommand, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .memberEmail(memberCreateCommand.memberEmail())
                .password(bCryptPasswordEncoder.encode(memberCreateCommand.password()))
                .memberName(memberCreateCommand.memberName())
                .birthDate(LocalDate.parse(memberCreateCommand.birthDate(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                .build();
    }

    public static Member from(MemberEntity memberEntity) {
        return Member.builder()
                .id(memberEntity.getId())
                .memberEmail(memberEntity.getMemberEmail())
                .password(memberEntity.getPassword())
                .memberName(memberEntity.getMemberName())
                .birthDate(memberEntity.getBirthDate())
                .createdDate(memberEntity.getCreatedDate())
                .updatedDate(memberEntity.getUpdatedDate())
                .build();
    }

}
