package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GdspeduGosiCrawlerTest {
    @Autowired
    private GdspeduGosiCrawler gdspeduGosiCrawler;

    @Test
    void testCrawling() throws InterruptedException, SSLException {
        gdspeduGosiCrawler.crawling(3);
    }

}