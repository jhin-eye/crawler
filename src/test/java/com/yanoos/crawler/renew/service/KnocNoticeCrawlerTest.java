package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.SSLException;

@SpringBootTest
class KnocNoticeCrawlerTest {

    @Autowired
    private KnocNoticeCrawler knocNoticeCrawler;

    @Test
    void contextLoads() throws InterruptedException, SSLException {
        knocNoticeCrawler.crawling(3);
    }

}