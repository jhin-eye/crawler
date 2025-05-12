package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GeochangNoticeCrawlerTest {
    @Autowired
    private GeochangNoticeCrawler geochangNoticeCrawler;

    @Test
    void crawling() {
        try {
            geochangNoticeCrawler.crawling(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
    }

}