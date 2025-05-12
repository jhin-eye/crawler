package com.yanoos.global.entity.board;

import com.yanoos.crawler.util.template.Crawler;
import com.yanoos.global.entity.dto.BoardDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/*

create table board(
	board_id bigserial primary key,
	board_name_eng varchar(255) not null,
	board_name_kor varchar(255) not null,
	board_url text unique not null,
	board_type_id bigint not null,
	constraint fk_board_type_id foreign key(board_type_id) references board_type(board_type_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_name_eng", nullable = false, length = 255)
    private String nameEng;

    @Setter
    @Column(name = "board_name_kor", nullable = false, length = 255)
    private String nameKor;

    @Column(name = "board_class_name", nullable = false, length = 255)
    private String className;

    @Column(name = "board_url", nullable = false, columnDefinition = "text")
    private String url;
    @Column(name = "site_url", nullable = false, columnDefinition = "text")
    private String siteUrl;
    @Column(name = "last_crawled_at")
    private ZonedDateTime lastCrawledAt;
    @Column(name = "previous_crawled_at")
    private ZonedDateTime previousCrawledAt;

    @ManyToOne
    @JoinColumn(name = "board_type_id", nullable = false)
    @Setter
    private BoardType type;

    @Column(name="page_size",nullable = false)
    @Setter
    private int pageSize;

    public static Board from(BoardDto boardDto) {
        return Board.builder()
                .id(boardDto.getId())
                .nameEng(boardDto.getNameEng())
                .nameKor(boardDto.getNameKor())
                .className(boardDto.getClassName())
                .url(boardDto.getUrl())
                .type(boardDto.getType())
                // .siteUrl(boardDto.getSiteUrl())
                .build();
    }
    public static Board from(Crawler crawler) {
        return Board.builder()
                .nameEng(crawler.boardNameEng)
                .nameKor(crawler.boardNameKor)
                .className(crawler.getClass().getSimpleName())
                .url(crawler.basePathUrl)
                .siteUrl(crawler.siteUrl)
                .build();
    }
    public static Board from(com.yanoos.crawler.renew.service.Crawler crawler) {
        return Board.builder()
                .nameEng(crawler.getBoardNameEng())
                .nameKor(crawler.getBoardNameKor())
                .className(crawler.getClass().getSimpleName())
                .url(crawler.getBasePathUrl())
                .siteUrl(crawler.getSiteUrl())
                .pageSize(crawler.getPageSize())
                .build();
    }

    public BoardDto toDto() {
        return BoardDto.builder()
                .id(this.id)
                .nameEng(this.nameEng)
                .nameKor(this.nameKor)
                .className(this.className)
                .url(this.url)
                .type(this.type)
                .build();
    }

    public void updateLastCrawledAt() {

        this.previousCrawledAt = this.lastCrawledAt;
        this.lastCrawledAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }
    public void updateLastCrawledAt(ZonedDateTime start) {
        this.previousCrawledAt = this.lastCrawledAt;
        this.lastCrawledAt = start;
    }
}
