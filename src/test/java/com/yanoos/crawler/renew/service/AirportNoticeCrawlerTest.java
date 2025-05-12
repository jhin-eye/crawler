package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AirportNoticeCrawlerTest {

    @Autowired
    private AirportNoticeCrawler airportNoticeCrawler;
    @Test
    void crawling() {
        try {
            airportNoticeCrawler.crawling(3);
        } catch (InterruptedException | SSLException e) {
            e.printStackTrace();
        }
    }


}