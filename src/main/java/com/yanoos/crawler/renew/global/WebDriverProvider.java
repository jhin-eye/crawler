package com.yanoos.crawler.renew.global;


import com.yanoos.crawler.util.util.SystemUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Slf4j
@Component
public class WebDriverProvider {
    private static final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();
    private static final List<WebDriver> allDrivers = Collections.synchronizedList(new ArrayList<>());

    public WebDriverProvider(){
        Runtime.getRuntime().addShutdownHook(new Thread(this::quitAllDriver));
    }

    public WebDriver getDriver(String browser){
        if(webDriverThreadLocal.get()==null){
            WebDriver driver = createWebDriver(browser);
            webDriverThreadLocal.set(driver);
            allDrivers.add(driver);
        }
        return webDriverThreadLocal.get();
    }

    private WebDriver createWebDriver(String browser){
        if("firefox".equalsIgnoreCase(browser)){
            return createFirefoxDriver();
        }else{
            return createChromeDriver();
        }
    }

    private WebDriver createChromeDriver(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if(!SystemUtil.isLocal()){
            options.addArguments("--headless");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("window-size=1920,1080");

        return new ChromeDriver(options);
    }

    private WebDriver createFirefoxDriver(){
        // WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if(!SystemUtil.isLocal()){
            options.addArguments("--headless");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("window-size=1920,1080");

        if (SystemUtil.isLocal()) { // 로컬 환경에서만 setup() 호출
            WebDriverManager.firefoxdriver().setup();
        }

        return new FirefoxDriver(options);
    }

    public void quitDriver(){
        WebDriver driver = webDriverThreadLocal.get();
        if(driver!=null){
            driver.quit();
            webDriverThreadLocal.remove();
        }
    }

    public void quitAllDriver(){

        synchronized (allDrivers){
            log.info("Quit all drivers");
            log.info("allDrivers size = {}", allDrivers.size());
            for(WebDriver driver : allDrivers){
                driver.quit();
            }
            allDrivers.clear();
        }
    }
}
