package com.yanoos.crawler.util.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.Set;

@Slf4j
public class DriverUtil {

    public static void removePopups(WebDriver driver) throws InterruptedException {
        Thread.sleep(10000);
        log.info("Removing popups");
        // 현재 윈도우 핸들 저장
        String mainWindowHandle = driver.getWindowHandle();
        log.info("Main window title: {}", driver.getTitle());

        // 모든 윈도우 핸들 가져오기
        Set<String> windowHandles = driver.getWindowHandles();
        log.info("Number of windows: {}", windowHandles.size());

        for (String handle : windowHandles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle); // 팝업 창으로 전환
                log.info("Popup window title: {}", driver.getTitle());
                driver.close(); // 팝업 창 닫기
            }
        }

        // 원래 창으로 다시 전환
        driver.switchTo().window(mainWindowHandle);
    }
}
