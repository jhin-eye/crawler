package com.yanoos.board.service;


import com.yanoos.board.repository.BoardRepository;
import com.yanoos.boardtype.BoardTypeService;
import com.yanoos.global.entity.board.Board;
import com.yanoos.global.entity.board.BoardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardTypeService boardTypeService;
    public void saveAll(List<Board> boards) {
        boardRepository.saveAll(boards);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }

    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    public Board findByClassName(String className) {
        return boardRepository.findByClassName(className).orElseThrow(
                () -> new IllegalArgumentException("Board not found with className: " + className)
        );
    }
}
