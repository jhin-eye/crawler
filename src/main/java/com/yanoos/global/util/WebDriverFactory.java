package com.yanoos.global.util;

import com.yanoos.crawler.util.constant.DevelopMentStage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    public static WebDriver driver;

    public static WebDriver getWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        FirefoxOptions options = new FirefoxOptions();

        // 도커 환경 또는 테스트가 아닌 경우 headless 모드 설정
        if(!System.getProperty("os.name").contains("Windows")) {
            options.addArguments("--headless");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("window-size=1920,1080");

        // 버전 명시 (옵션)
//        WebDriverManager.chromedriver().setup();  // 또는 .browserVersion("해당 Chrome 버전").setup();
//         WebDriverManager.chromedriver().driverVersion("128.0.6613.86").setup();
        // driver = new ChromeDriver(options);
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();


        return driver;
    }
}
