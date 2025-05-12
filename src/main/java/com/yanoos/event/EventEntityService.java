package com.yanoos.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yanoos.global.entity.board.Board;
import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.event.Event;
import com.yanoos.global.entity.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventEntityService {
    private final EventRepository eventRepository;

    public List<Event> getEventsByPublished(Boolean published) {
        return eventRepository.findByPublishedOrderByIdAsc(published);
    }


    @Transactional
    public void outBoxNewPosts(List<Post> postsForSave) {
        List<Event> events = postsForSave.stream().map(post -> {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("postId", post.getId());

            return Event.builder()
                    .eventType(EventType.NEW_POST.name())
                    .eventData(jsonObject.toString())
                    .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                    .published(false)
                    .tryCount(0L)
                    .build();
        }).toList();

        eventRepository.saveAll(events);
    }

    @Transactional
    public void outBoxFailCrawling(Board board) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("boardClass",board.getClassName());
        jsonObject.put("boardId", board.getId());

        Event event = Event.builder()
                .eventType(EventType.FAIL_CRAWLING.name())
                .eventData(jsonObject.toString())
                .createdAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")))
                .published(false)
                .tryCount(0L)
                .build();

        eventRepository.save(event);
    }

    @Transactional
    public void outBoxCrawlingStatus(CrawlingStatus crawlingStatus, ZonedDateTime now) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("crawlingStatus", crawlingStatus.name());

        Event event = Event.builder()
                .eventType(EventType.CRAWLING_STATUS.name())
                .eventData(jsonObject.toString())
                .createdAt(now)
                .published(false)
                .tryCount(0L)
                .build();

        eventRepository.save(event);
    }
}
