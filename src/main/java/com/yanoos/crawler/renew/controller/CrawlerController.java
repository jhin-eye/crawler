package com.yanoos.crawler.renew.controller;

import com.yanoos.crawler.renew.service.CrawlerService;
import com.yanoos.global.entity.dto.PostDto;
import com.yanoos.scheduler.CrawlerScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

// @RestController
@RestController
@RequiredArgsConstructor
@Profile("!test")
@RequestMapping("/crawling")
public class CrawlerController {
    private final CrawlerScheduler crawlerScheduler;
    private final CrawlerService crawlerService;

    @PostMapping("/test")
    public ResponseEntity<String> testCrawling(){
        crawlerService.synchronizeCrawlerWithBoards();
        crawlerScheduler.executeCrawling();
        return ResponseEntity.ok("Crawling executed");
    }

}
