package com.yanoos.member.repository.post;

import com.yanoos.global.entity.board.Board;
import com.yanoos.global.entity.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    public Post findByNoAndTitleAndBoard(String no, String title, Board board);

}
