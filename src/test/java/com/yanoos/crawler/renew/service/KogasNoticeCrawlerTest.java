package com.yanoos.crawler.renew.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import javax.net.ssl.SSLException;

@SpringBootTest
@ActiveProfiles("test")
class KogasNoticeCrawlerTest {

    @Autowired
    private KogasNoticeCrawler kogasNoticeCrawler;

    @Test
    void test() throws InterruptedException, SSLException {
        for(int i=3;i>0;i--){
            kogasNoticeCrawler.crawling(i);
        }
    }

}