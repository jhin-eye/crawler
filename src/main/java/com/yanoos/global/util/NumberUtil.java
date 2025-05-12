package com.yanoos.global.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberUtil {
    public static boolean isNumberBetween0And9(String text) {
        String onlyNumbers = text.replaceAll("[^0-9]", "");
        // log.info("checkText is {}",onlyNumbers);
        return onlyNumbers.matches("^[0-9]$");
    }

    public static String extractNumber(String jsFunction) {
        // 정규식을 사용하여 숫자만 추출
        String number = jsFunction.replaceAll("[^\\d]", "");
        return number;
    }
}
