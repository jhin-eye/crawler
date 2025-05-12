package com.yanoos.crawler.renew.service;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DobongGosiCrawlerTest {
    @Autowired
    DobongGosiCrawler dobongGosiCrawler;

    @Test
    void test() throws InterruptedException, SSLException {
        dobongGosiCrawler.crawling(3);
    }

}