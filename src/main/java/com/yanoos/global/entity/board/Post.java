package com.yanoos.global.entity.board;

import com.yanoos.global.entity.dto.PostDto;
import com.yanoos.global.entity.member.MapMemberPost;
import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post",
        uniqueConstraints = @UniqueConstraint(columnNames = {"board_id", "post_no", "post_title"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "post_no", nullable = false, length = 255)
    private String no;

    @Column(name = "post_title", nullable = false, length = 255)
    private String title;

    @Column(name = "post_content", columnDefinition = "text")
    private String content;

    @Column(name = "post_write_date")
    private ZonedDateTime writeDate;

    @Column(name = "post_department", nullable = false, length = 255)
    private String department;

    @Column(name = "post_url", nullable = false, columnDefinition = "text")
    private String url;

    @Column(name = "monitor_time", nullable = false)
    private ZonedDateTime monitorTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MapMemberPost> mapMemberPost = new ArrayList<>();

    @Column(name = "method", nullable = false, columnDefinition = "text")
    private String method;
    @Column(name = "endpoint", nullable = false, columnDefinition = "text")
    private String endpoint;
    @Column(name = "parameters", nullable = false, columnDefinition = "text")
    private String parameters;

    @Column(name = "header_parameters", nullable = false, columnDefinition = "text")
    private String headerParameters;

    @Column(name = "board_name_eng", nullable = false, length = 255)
    private String boardNameEng;

    public PostDto toDto(){
        return PostDto.builder()
                .id(this.id)
                .boardDto(this.board.toDto())
                .no(this.no)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate)
                .department(this.department)
                .url(this.url)
                .monitorTime(this.monitorTime)
                .build();
    }
     public static Post from(PostDto postDto){
        return Post.builder()
                .id(postDto.getId())
                .board(Board.from(postDto.getBoardDto()))
                .no(postDto.getNo())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writeDate(postDto.getWriteDate())
                .department(postDto.getDepartment())
                .url(postDto.getUrl())
                .monitorTime(postDto.getMonitorTime())
                .method(postDto.getMethod())
                .endpoint(postDto.getEndpoint())
                .parameters(postDto.getParameters())
                .boardNameEng(postDto.getBoardDto().getNameEng())
                .build();
    }


    public void addMapMemberPost(MapMemberPost mapMemberPost) {
        this.mapMemberPost.add(mapMemberPost);
    }
}