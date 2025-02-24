package com.yjkim.lezhin.member.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Member {
    private Long id;
    private String memberEmail;
    private String password;
    private String memberName;
    private LocalDate birthDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


}
