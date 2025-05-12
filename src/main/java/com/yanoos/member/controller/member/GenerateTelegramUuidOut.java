package com.yanoos.member.controller.member;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class GenerateTelegramUuidOut {
    private Long memberId;
    private UUID uuid;
}
