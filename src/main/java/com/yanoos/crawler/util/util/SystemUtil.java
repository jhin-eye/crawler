package com.yanoos.crawler.util.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 시스템 관련 유틸리티 클래스
 * - OS 환경을 체크하여 로컬 환경 여부를 확인하는 기능 포함
 */
@Slf4j
public class SystemUtil {

    /**
     * OS 정보를 가져오는 메서드 (Windows가 아니면 기본값 Linux)
     */
    private static String getOS() {
        return (System.getenv("OS") == null) ? "Linux" : System.getenv("OS");
    }

    /**
     * 현재 OS가 Windows인지 확인하는 메서드
     * @return true: Windows 환경, false: Linux 또는 기타 환경
     */
    public static boolean isLocal() {
        return getOS().contains("Windows");
    }

    /**
     * 테스트 로그 출력 (Windows 환경에서만 실행)
     */
    public static void testLog(String s, Object... args) { // 가변 인자 처리
        if (isLocal()) {
            log.info(s, args);
        }
    }

    public static void testLog(String s) {
        if (isLocal()) {
            log.info(s);
        }
    }
}
