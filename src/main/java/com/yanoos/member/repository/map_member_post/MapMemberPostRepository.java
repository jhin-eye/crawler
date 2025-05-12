package com.yanoos.member.repository.map_member_post;

import com.yanoos.global.entity.member.MapMemberPost;
import com.yanoos.global.entity.member.Member;
import com.yanoos.global.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapMemberPostRepository extends JpaRepository<MapMemberPost, Long> {
    public List<MapMemberPost> findByMemberOrderByIdDesc(Member member);

    public List<MapMemberPost> findByPost(Post post);

}
