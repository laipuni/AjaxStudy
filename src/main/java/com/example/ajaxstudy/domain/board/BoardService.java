package com.example.ajaxstudy.domain.board;

import com.example.ajaxstudy.domain.board.request.BoardAddRequest;
import com.example.ajaxstudy.domain.board.response.BoardAddResponse;
import com.example.ajaxstudy.domain.board.response.BoardDetailResponse;
import com.example.ajaxstudy.domain.board.response.BoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardAddResponse post(BoardAddRequest request){
        Board board = request.toEntity();
        boardRepository.save(board);
        return BoardAddResponse.of(board);
    }

    public List<BoardListResponse> findAllDesc(){
        return boardRepository.findAllOrderByBoardIdDesc().stream()
                .map(BoardListResponse::of)
                .toList();
    }

    public BoardDetailResponse findById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        return BoardDetailResponse.of(board);
    }
}
