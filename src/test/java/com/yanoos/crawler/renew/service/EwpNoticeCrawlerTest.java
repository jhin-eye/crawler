package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EwpNoticeCrawlerTest {
    @Autowired
    private EwpNoticeCrawler ewpNoticeCrawler;

    @Test
    void crawling() throws InterruptedException, SSLException {

        ewpNoticeCrawler.crawling(3);

    }

}