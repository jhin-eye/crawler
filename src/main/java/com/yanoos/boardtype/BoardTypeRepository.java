package com.yanoos.boardtype;

import com.yanoos.global.entity.board.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {
    List<BoardType> findAllByOrderByIdAsc();

    Optional<BoardType> findByName(String name);
}
