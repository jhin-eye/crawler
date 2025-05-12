package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class SeoulBidCrawlerTest {

    @Autowired
    private SeoulBidCrawler seoulBidCrawler;

    @Test
    void testCrawling() throws InterruptedException {
        try {
            seoulBidCrawler.crawling(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}