package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class KospoNoticeCrawlerTest {
    @Autowired
    private KospoNoticeCrawler kospoNoticeCrawler;

    @Test
    void test() throws InterruptedException, SSLException {
        kospoNoticeCrawler.crawling(3);
    }

}