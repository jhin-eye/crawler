package com.yanoos.member.controller.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostTestInDTO {
    private String boardName;
    private String boardUrl;
    private String postTitle;
    private String postUrl;
}
