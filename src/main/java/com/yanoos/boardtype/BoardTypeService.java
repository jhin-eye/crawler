package com.yanoos.boardtype;

import com.yanoos.global.entity.board.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTypeService {
    private final BoardTypeRepository boardTypeRepository;

    public List<BoardType> findAll() {
        return boardTypeRepository.findAllByOrderByIdAsc();
    }
    public BoardType findByName(String name) {
        return boardTypeRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("해당 게시판 타입이 존재하지 않습니다."));
    }
}
