package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JongnoNoticeCrawlerTest {
    @Autowired
    private JongnoNoticeCrawler jongnoNoticeCrawler;

    @Test
    void crawling(){
        try {
            jongnoNoticeCrawler.crawling(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}