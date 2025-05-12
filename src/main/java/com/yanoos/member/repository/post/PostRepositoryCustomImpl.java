package com.yanoos.member.repository.post;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yanoos.global.entity.board.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.yanoos.global.entity.board.QPost.post;



@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> findByMemberId(Long memberId, Pageable pageable) {
        return List.of();
    }

    @Override
    public Post findByNoAndTitleAndBoardId(String no, String title, Long boardId) {
        Post result = jpaQueryFactory.selectFrom(post)
                .where(post.no.eq(no)
                        .and(post.title.eq(title))
                        .and(post.board.id.eq(boardId)))
                .fetchOne();
        return result;
    }
}
