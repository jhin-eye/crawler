package com.yanoos.crawler.renew.service;

import com.yanoos.crawler.renew.global.WebDriverProvider;
import com.yanoos.scheduler.CrawlerScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class KamcoNoticeCrawlerTest {

    @Autowired
    private KamcoNoticeCrawler kamcoNoticeCrawler;

    @Test
    void test() throws InterruptedException, SSLException {
        for(int i=3;i>0;i--){
            kamcoNoticeCrawler.crawling(i);
        }
    }
}