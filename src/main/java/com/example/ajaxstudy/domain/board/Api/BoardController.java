package com.example.ajaxstudy.domain.board.Api;

import com.example.ajaxstudy.domain.board.BoardService;
import com.example.ajaxstudy.domain.board.response.BoardDetailResponse;
import com.example.ajaxstudy.domain.board.response.BoardListResponse;
import com.example.ajaxstudy.domain.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/boards")
    public String boards(Model model){
        List<BoardListResponse> boards = boardService.findAllDesc();
        model.addAttribute("boards",boards);
        return "board/boards";
    }

    @GetMapping("/boards/{boardId}")
    public String boardDetail(@PathVariable("boardId")Long boardId, Model model){
        BoardDetailResponse board = boardService.findById(boardId);
        model.addAttribute("board",board);
        model.addAttribute("boardId",boardId);
        return "board/boardDetail";
    }

}
