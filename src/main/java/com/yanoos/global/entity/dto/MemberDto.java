package com.yanoos.global.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MemberDto {
    private Long memberId;
    private String memberEmail;
    private String memberNickname;
}
