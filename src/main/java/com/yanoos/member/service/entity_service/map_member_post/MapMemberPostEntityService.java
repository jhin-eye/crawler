package com.yanoos.member.service.entity_service.map_member_post;

import com.yanoos.global.entity.member.MapMemberPost;
import com.yanoos.global.entity.member.Member;
import com.yanoos.global.entity.board.Post;
import com.yanoos.member.repository.map_member_post.MapMemberPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MapMemberPostEntityService {
    private final MapMemberPostRepository mapMemberPostRepository;

    public List<MapMemberPost> getMapMemberPostsByMember(Member member) {
        return mapMemberPostRepository.findByMemberOrderByIdDesc(member);
    }

    public List<MapMemberPost> getMapMemberPostsByPostId(Post post) {
        return mapMemberPostRepository.findByPost(post);
    }

    public MapMemberPost getByMapMemberPostId(Long mapMemberPostId) {
        return mapMemberPostRepository.findById(mapMemberPostId).orElseThrow();
    }

    public MapMemberPost saveMapMemberPost(MapMemberPost mapMemberPost) {
        mapMemberPostRepository.save(mapMemberPost);
        return mapMemberPost;
    }
}
