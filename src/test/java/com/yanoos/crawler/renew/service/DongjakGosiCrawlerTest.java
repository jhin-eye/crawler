package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DongjakGosiCrawlerTest {
    @Autowired
    private DongjakGosiCrawler dongjakGosiCrawler;

    @Test
    void testCrawling() throws InterruptedException, SSLException {
        dongjakGosiCrawler.crawling(2);
    }

}