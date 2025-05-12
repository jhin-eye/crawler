// package com.yanoos.board.service;
//
// import com.yanoos.board.repository.BoardRepository;
// import com.yanoos.crawler.renew.service.CrawlerService;
// import com.yanoos.crawler.util.util.SystemUtil;
// import com.yanoos.event.EventEntityService;
// import com.yanoos.global.entity.board.Board;
// import com.yanoos.global.entity.dto.PostDto;
// import com.yanoos.global.exception.BusinessException;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Propagation;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.lang.reflect.InvocationTargetException;
// import java.time.ZonedDateTime;
// import java.util.List;
//
//
// @Service
// @RequiredArgsConstructor
// @Slf4j
// public class BoardProcessService {
//     private final CrawlerService crawlerService;
//     private final EventEntityService eventEntityService;
//     private final BoardRepository boardRepository;
//
//     @Transactional(propagation = Propagation.REQUIRES_NEW)
//     public boolean processBoard(Board board, ZonedDateTime start) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
//         ZonedDateTime beforeCrawledTime = board.getLastCrawledAt();
//         if (!SystemUtil.isLocal() && beforeCrawledTime != null && beforeCrawledTime.isAfter(start.minusMinutes(10))) {
//             log.info("Skip crawling board: {}", board.getNameEng());
//         } else {
//             for (int page = 3; page > 0; page--) {
//                 log.info("Crawling page: {}", page);
//                 List<PostDto> crawling = crawlerService.crawling(board.getId(), page);
//             }
//             board.updateLastCrawledAt(start);
//             boardRepository.save(board);
//         }
//         return true;
//     }
// }
