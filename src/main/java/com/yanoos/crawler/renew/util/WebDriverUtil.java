package com.yanoos.crawler.renew.util;

import com.yanoos.crawler.util.constant.HtmlTagType;
import com.yanoos.global.entity.dto.PostDto;
import com.yanoos.global.exception.BusinessException;
import com.yanoos.global.exception.code.CrawlerErrorCode;
import com.yanoos.global.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebDriverUtil {
    /**
     * 페이지가 완전히 로드될 때까지 대기
     *
     * @param driver  WebDriver 인스턴스
     * @param timeout 최대 대기 시간 (초)
     * @throws TimeoutException 페이지 로드 시간이 초과된 경우 예외 발생
     */
    public static void waitForPageLoad(WebDriver driver, int timeout) throws InterruptedException {
        Thread.sleep(2000);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (timeout * 1000L);
        int pollingInterval = 500; // 상태 확인 주기 (밀리초)

        while (System.currentTimeMillis() < endTime) {

            // `document.readyState` 값 확인
            String readyState = jsExecutor.executeScript("return document.readyState").toString();
            if ("complete".equals(readyState)) {
                return; // 페이지 로드 완료
            }
            Thread.sleep(pollingInterval); // 대기 후 다시 확인

        }

        throw new TimeoutException("페이지 로드 시간 초과: " + timeout + "초");
    }

    public static void waitForPageLoadByElement(WebDriver driver, int timeout, String xpath, String str) throws InterruptedException {
        Thread.sleep(2000);
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (timeout * 1000L);
        int pollingInterval = 500; // 상태 확인 주기 (밀리초)

        while (System.currentTimeMillis() < endTime) {
            try {
                WebElement element = driver.findElement(By.xpath(xpath));
                if (element.getText().equals(str)) {
                    return; // 페이지 로드 완료
                }
                Thread.sleep(pollingInterval); // 대기 후 다시 확인
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 현재 쓰레드 인터럽트 상태 유지
                throw new RuntimeException("페이지 로드 대기 중 인터럽트 발생", e);
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        throw new TimeoutException("페이지 로드 시간 초과: " + timeout + "초");
    }

    public static void movePage(WebDriver driver, int targetPage, String pageButtonXpath) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,3000)");

        if (targetPage == 1) {
            return;
        }
        WebElement targetPageButton = getTargetPageButton(driver, targetPage, pageButtonXpath);
        targetPageButton.click();
        waitForPageLoad(driver, 30);
    }
    public static void movePageUsingJs(WebDriver driver, int targetPage, String pageButtonXpath) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,3000)");

        if (targetPage == 1) {
            return;
        }
        WebElement targetPageButton = getTargetPageButton(driver, targetPage, pageButtonXpath);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", targetPageButton);
        waitForPageLoad(driver, 30);
    }

    private static WebElement getTargetPageButton(WebDriver driver, int targetPageNumber, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        List<WebElement> pageButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));

        for (WebElement pageButton : pageButtons) {
            String pageNumber = pageButton.getText();
            if (!NumberUtil.isNumberBetween0And9(pageNumber) && System.getProperty("os.name").contains("Windows")) {
                // log.info("{} is not a number", pageNumber);
                continue;
            } else {
                // log.info("{} is a number", pageNumber);
                try {
                    pageNumber = pageNumber.replaceAll("[^0-9]", "");
                    Integer.parseInt(pageNumber);
                } catch (NumberFormatException e) {
                    // log.info("{} is not a number!!!!!", pageNumber);
                    continue;
                }
            }

            int curPageNumber = Integer.parseInt(pageNumber);
            if (System.getProperty("os.name").contains("Windows")) {
                log.info("current page number is {}", curPageNumber);
            }
            if (curPageNumber == targetPageNumber) {
                // log.info("curPageNumber {} == targetPageNumber {}", curPageNumber, targetPageNumber);
                return pageButton;
            }
        }

        throw new BusinessException(CrawlerErrorCode.CANNOT_GET_TARGET_PAGE_BUTTON);
    }
}
