package com.yanoos.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CrawlerErrorCode implements ErrorCode{
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "해당 토큰은 유효한 토큰이 아닙니다."),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "유효한 토큰 타입이 아닙니다(ACCESS, REFRESH 확인). 필요한 토큰 타입: {0}"),
    INVALID_COLUMN_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "해당 컬럼 타입은 지원하지 않습니다."),
    DEVELOPMENT_STAGE_BREAK(HttpStatus.I_AM_A_TEAPOT,"개발 목적으로 break, 현재 stage : {0}"),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST,"잘못된 형식: {0}"), 
    INVALID_ROW_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "해당 로우 타입은 지원하지 않습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 게시판명 {0}" ),
    CRAWLER_CLASS_NOT_FOUND(HttpStatus.NOT_FOUND,"클래스명을 찾지 못하는 상태{0}" ),
    CRAWLER_METHOD_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "크롤러 메소드 호출 중 에러 발생 {0}" ), 
    MOVE_PAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "페이지 이동 실패, 이동 페이지 : {}" ),
    CANNOT_GET_TARGET_PAGE_BUTTON(HttpStatus.INTERNAL_SERVER_ERROR, "이동할 페이지 버튼 탐색 실패");
    private final HttpStatus httpStatus;
    private final String message;
}
