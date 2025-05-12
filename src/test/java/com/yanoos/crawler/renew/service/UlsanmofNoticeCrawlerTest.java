package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UlsanmofNoticeCrawlerTest {
    @Autowired
    private UlsanmofNoticeCrawler ulsanmofNoticeCrawler;

    @Test
    public void testCrawling() throws InterruptedException, SSLException {
        ulsanmofNoticeCrawler.crawling(3);
    }

}