package com.yjkim.lezhin.member.infrastructure.entity;

import com.yjkim.lezhin.common.domain.entity.BaseTimeEntity;
import com.yjkim.lezhin.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberEmail;

    @Column(nullable = false)
    private String password;

    private String memberName;
    private LocalDate birthDate;

    @Builder
    private MemberEntity(Long id, String memberEmail, String password, String memberName, LocalDate birthDate) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.password = password;
        this.memberName = memberName;
        this.birthDate = birthDate;
    }

    public static MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .memberEmail(member.getMemberEmail())
                .password(member.getPassword())
                .memberName(member.getMemberName())
                .birthDate(member.getBirthDate())
                .build();
    }
}
