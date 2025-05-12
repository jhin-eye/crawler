package com.yanoos.member.service.entity_service.post;

import com.yanoos.global.entity.board.Post;
import com.yanoos.member.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PostEntityService {
    private final PostRepository postRepository;

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }
}
