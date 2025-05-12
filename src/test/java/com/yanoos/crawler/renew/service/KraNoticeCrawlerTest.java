package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class KraNoticeCrawlerTest {
    @Autowired
    private KraNoticeCrawler kraNoticeCrawler;

    @Test
    void crawling() {
        try {
            kraNoticeCrawler.crawling(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}