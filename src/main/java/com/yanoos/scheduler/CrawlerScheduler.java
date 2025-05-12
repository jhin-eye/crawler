package com.yanoos.scheduler;

import com.yanoos.board.repository.BoardRepository;
// import com.yanoos.board.service.BoardProcessService;
import com.yanoos.board.service.BoardService;
import com.yanoos.crawler.renew.service.Crawler;
import com.yanoos.crawler.renew.service.CrawlerService;
import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.event.CrawlingStatus;
import com.yanoos.event.ErrorLogEntityService;
import com.yanoos.event.EventEntityService;
import com.yanoos.global.entity.board.Board;
import com.yanoos.global.entity.event.ErrorLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLException;
import java.lang.reflect.InvocationTargetException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class CrawlerScheduler {

    private final CrawlerService crawlerService;
    private final BoardRepository boardRepository;
    private final EventEntityService eventEntityService;
    // private final BoardProcessService boardProcessService;
    private final ErrorLogEntityService errorLogEntityService;
    private final ThreadPoolTaskScheduler taskScheduler;
    @Value("${scheduled.fixedDelay.crawling}")
    private long fixedDelay;
    private final List<Crawler> crawlers;
    private final BoardService boardService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        log.info("\n=====================\nApplicationReadyEvent {}\n=====================\n", Thread.currentThread().getId());
        crawlerService.synchronizeCrawlerWithBoards();
        log.info("\n=====================\nFINISH ApplicationReadyEvent {}\n=====================\n", Thread.currentThread().getId());
        taskScheduler.scheduleWithFixedDelay(this::executeCrawling, fixedDelay);
    }

    public void executeCrawling() {
        log.info("\n=====================\nexecuteCrawling {}\n=====================\n", Thread.currentThread().getId());

        ZonedDateTime start = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        eventEntityService.outBoxCrawlingStatus(CrawlingStatus.START, start);
        for (Crawler crawler : crawlers) {
            Board board = boardService.findByClassName(crawler.getClass().getSimpleName());
            //4시간이내 크롤링시 패스
            if (board.getLastCrawledAt() != null && board.getLastCrawledAt().plusHours(4).isAfter(start)) {
                log.info("Already crawled within 4 hours: {}", board.getNameEng());
                continue;
            }

            try {
                processCrawler(crawler, start);
                board.updateLastCrawledAt(start);
                boardRepository.save(board);
            } catch (Exception e) {
                e.printStackTrace();
                String fullStackTrace = ExceptionUtils.getStackTrace(e);
                ErrorLog errorLog = ErrorLog.builder()
                        .topic("Crawling")
                        .topic2(board.getNameEng())
                        .log(fullStackTrace)
                        .occurrenceTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                        .checked(false)
                        .build();
                errorLogEntityService.save(errorLog);
                eventEntityService.outBoxFailCrawling(board);
            }

        }
        ZonedDateTime end = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        eventEntityService.outBoxCrawlingStatus(CrawlingStatus.END, end);

        log.info("\n=====================\nFINISH SCHEDULING {}\n=====================\n", Thread.currentThread().getId());
    }


    public void processCrawler(Crawler crawler, ZonedDateTime start) throws InterruptedException, SSLException {
        crawlerService.crawling(crawler, start);
    }

    // public void executeCrawling() {
    //     log.info("\n=====================\nexecuteCrawling {}\n=====================\n", Thread.currentThread().getId());
    //     eventEntityService.outBoxCrawlingStatus(CrawlingStatus.START);
    //     ZonedDateTime start = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    //
    //     boardRepository.findAllByOrderByIdDesc().forEach(board -> {
    //         log.info("Crawling board: {}", board.getNameEng());
    //         try {
    //             boardProcessService.processBoard(board, start);
    //         }catch (ClassNotFoundException e){
    //             SystemUtil.testLog("refactoring needed {}",board.getNameEng());
    //         }
    //         catch (Exception e) {
    //             e.printStackTrace();
    //             String fullStackTrace = ExceptionUtils.getStackTrace(e);
    //             ErrorLog errorLog = ErrorLog.builder()
    //                     .topic("Crawling")
    //                     .topic2(board.getNameEng())
    //                     .log(fullStackTrace)
    //                     .occurrenceTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
    //                     .checked(false)
    //                     .build();
    //             errorLogEntityService.save(errorLog);
    //             eventEntityService.outBoxFailCrawling(board.getId());
    //         }
    //     });
    //
    //     eventEntityService.outBoxCrawlingStatus(CrawlingStatus.END);
    //     log.info("\n=====================\nFINISH SCHEDULING {}\n=====================\n", Thread.currentThread().getId());
    //
    // }
}
