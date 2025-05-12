package com.yanoos.global.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TimeUtil {
    public static ZonedDateTime parseWriteDateToZonedDatetime(String text) {
        ZonedDateTime result;
        ZoneId seoulZone = ZoneId.of("Asia/Seoul");

        // "yyyy-MM-dd" 형식인지 확인
        if (text.matches("\\d{4}-\\d{2}-\\d{2}")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // LocalDate로 파싱
            LocalDate localDate = LocalDate.parse(text, formatter);
            // ZonedDateTime으로 변환 (Asia/Seoul 시간대 기준)
            result = localDate.atStartOfDay(seoulZone);
        }
        // "yyyy-MM-dd HH:mm:ss" 형식인지 확인
        else if (text.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // LocalDateTime으로 파싱
            LocalDateTime localDateTime = LocalDateTime.parse(text, formatter);
            // ZonedDateTime으로 변환 (Asia/Seoul 시간대 기준)
            result = localDateTime.atZone(seoulZone);
        }
        // "yyyy.MM.dd" 형식 추가 (2024.10.07)
        else if (text.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            LocalDate localDate = LocalDate.parse(text, formatter);
            result = localDate.atStartOfDay(seoulZone);
        }
        // "yyyyMMdd" 형식 추가 (예: 20250328)
        else if (text.matches("\\d{8}")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(text, formatter);
            result = localDate.atStartOfDay(seoulZone);
        }
        // 형식이 맞지 않는 경우 기본값 리턴
        else {
            result = ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, seoulZone);
        }

        log.debug("Converted '{}' to ZonedDateTime: {}", text, result);
        return result;
    }
}
