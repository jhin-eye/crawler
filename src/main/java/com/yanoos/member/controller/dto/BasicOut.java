package com.yanoos.member.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class BasicOut {
    private boolean isSuccess;
    public BasicOut(){
        this.isSuccess = true;
    }
}
