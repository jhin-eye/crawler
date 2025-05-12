package com.yanoos.member.service.business_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateKeywordOut {
    private long keywordId;
    private String keyword;
}
