package com.yanoos.member.controller.test.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ValidTestInDTO {

    @NotBlank
    private String p1;
    @NotBlank
    private String p2;
}
