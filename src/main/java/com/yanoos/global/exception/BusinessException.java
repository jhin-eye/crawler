package com.yanoos.global.exception;

import com.yanoos.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private String editedMessage;

    public BusinessException(ErrorCode errorCode, Object... objects) {
        this.errorCode = errorCode;
        this.editedMessage = MessageFormat.format(errorCode.getMessage(), objects);
    }

    // editedMessage가 null일 때 기본 메시지로 초기화
    @Override
    public String getMessage() {
        return editedMessage != null ? editedMessage : errorCode.getMessage();
    }
}
