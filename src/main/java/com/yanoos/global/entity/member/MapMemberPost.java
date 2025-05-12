package com.yanoos.global.entity.member;

import com.yanoos.global.entity.board.Post;
import com.yanoos.global.entity.dto.MapMemberPostOut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "map_member_post")
@Builder
public class MapMemberPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_member_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "checked", nullable = false)
    private boolean checked;

    @Column(name = "keywords", columnDefinition = "text[]", nullable = false)
    private List<String> keywords;



    public MapMemberPostOut toDto(){
        return MapMemberPostOut.builder()
                .mapMemberPostId(this.id)
                .memberDto(member.toDto())
                .postDto(post.toDto())
                .checked(this.checked)
                .keywords(this.keywords)
                .build();
    }

    public void updateChecked() {
        this.checked = !this.checked;
    }
}