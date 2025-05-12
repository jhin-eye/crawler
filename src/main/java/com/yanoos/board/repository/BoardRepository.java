package com.yanoos.board.repository;

import com.yanoos.global.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByNameEng(String name);
    List<Board> findAllByOrderByIdDesc();

    Optional<Board> findByClassName(String className);
}
