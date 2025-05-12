package com.yanoos.crawler.renew.service;

import com.yanoos.board.service.BoardService;
import com.yanoos.boardtype.BoardTypeService;
import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.event.EventEntityService;
import com.yanoos.global.entity.board.Board;
import com.yanoos.global.entity.board.BoardType;
import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.dto.PostDto;
import com.yanoos.member.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CrawlerService {
    private final BoardService boardService;
    private final BoardTypeService boardTypeService;
    private final ApplicationContext applicationContext;
    private final PostRepository postRepository;
    private final EventEntityService eventEntityService;


    @Transactional
    public void synchronizeCrawlerWithBoards(){
        log.info("Synchronize Crawler with Boards");
        //모든 크롤러 구현체
        Map<String, Crawler> crawlerBeans = applicationContext.getBeansOfType(Crawler.class);
        //크롤러 빈 클래스명
        Set<String> crawlerClassNames = crawlerBeans.keySet();

        //DB에 저장된 게시판 목록
        List<Board> boards = boardService.getAllBoards();

        //DB에 저장된 게시판 목록 중 크롤러 빈 클래스명과 일치하지 않는 게시판 삭제
        boards.stream()
                .filter(board -> !crawlerClassNames.contains(board.getClassName()))
                .forEach(board -> {
                    boardService.deleteBoard(board);
                });
        boards = boardService.getAllBoards();

        //크롤러 빈 클래스명과 일치하지 않는 게시판 추가
        // Map<String, BoardType> boardTypeMap = boardTypeService.findAll().stream().collect(Collectors.toMap(BoardType::getName, Function.identity()));
        // ✅ 일반 for문 사용하여 람다 문제 해결
        for (String crawlerClassName : crawlerClassNames) {
            boolean exists = false;
            for (Board board : boards) {
                if (board.getClassName().equals(crawlerClassName)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                Crawler crawler = applicationContext.getBean(crawlerClassName, Crawler.class);
                Board board = Board.from(crawler);
                BoardType boardType = boardTypeService.findByName(crawler.getBoardTypeName());
                board.setType(boardType);
                boardService.saveBoard(board);
            }
        }
        //DB에 있는 게시판과 크롤러 빈 클래스 필드 비교 후 업데이트
        for(Board board : boards){
            log.info("board: {}, {}",board.getNameEng(), board.getClassName());
            Crawler crawler = crawlerBeans.get(board.getClassName());
            compareBoardWithCrawler(board, crawler);
        }
        log.info("Synchronize Crawler with Boards Done");
    }

    private void compareBoardWithCrawler(Board board, Crawler crawler) {
        boolean isUpdated = false;
        if(board.getPageSize() != crawler.getPageSize()){
            board.setPageSize(crawler.getPageSize());
            isUpdated = true;
        }
        if(board.getNameKor() != null && !board.getNameKor().equals(crawler.getNameKor())){
            board.setNameKor(crawler.getNameKor());
            isUpdated = true;
        }

        if(isUpdated){
            boardService.saveBoard(board);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void crawling(Crawler crawler, ZonedDateTime start) throws InterruptedException, SSLException {
        log.info("\033[1;34m======================= [ CRAWLING START ] =======================\033[0m"); // 파란색
        log.info("\033[1;32m  Target Crawler : {} \033[0m", crawler.getClass().getSimpleName()); // 초록색
        log.info("\033[1;34m==================================================================\033[0m"); // 파란색

        Board board = boardService.findByClassName(crawler.getClass().getSimpleName());

        for(int page=3; page>0; page--){
            log.info("\n---------- NOW CRAWLING ----------");
            log.info("  Crawler : {}", crawler.getClass().getSimpleName());
            log.info("  Page    : {}", page);
            log.info("----------------------------------\n");
            List<PostDto> postDtos = crawler.crawling(page);
            postDtos.forEach(postDto -> {
                postDto.setBoardDto(board.toDto());
            });

            List<PostDto> filteredPostDtos = filterExistPosts(postDtos);
            Collections.reverse(filteredPostDtos);
            List<Post> postsForSave = filteredPostDtos.stream().map(Post::from).toList();
            postsForSave.forEach(post -> post.setMonitorTime(start));
            postRepository.saveAll(postsForSave);
            //이벤트 outbox
            eventEntityService.outBoxNewPosts(postsForSave);
        }
    }

    private List<PostDto> filterExistPosts(List<PostDto> postDtos) {
        List<PostDto> deduplicationPostDtos = postDtos.stream().distinct().toList();
        // log.info("deduplicationPostDtos size: {}", deduplicationPostDtos.size());
        List<PostDto> filteredPostDtos = deduplicationPostDtos.stream().filter(postDto -> !checkPostExist(postDto)).collect(Collectors.toList());
        // log.info("filteredPostDtos size: {}", filteredPostDtos.size());
        return filteredPostDtos;


    }

    private boolean checkPostExist(PostDto postDto) {
        Post post = postRepository.findByNoAndTitleAndBoard(postDto.getNo(), postDto.getTitle(), Board.from(postDto.getBoardDto()));
        if(post==null){
            return false;
        }else {
            return true;
        }
    }
}
