package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class KomipoNoticeCrawlerTest {
    @Autowired
    private KomipoNoticeCrawler komipoNoticeCrawler;

    @Test
    void crawling() {
        try {
            komipoNoticeCrawler.crawling(3);
        } catch (InterruptedException | SSLException e) {
            e.printStackTrace();
        }
    }


}