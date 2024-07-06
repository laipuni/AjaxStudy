package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.PageAbleFactory;
import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentChildRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;
import com.example.ajaxstudy.domain.comment.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public CommentAddResponse record(CommentAddRequest request){
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));
        Comment comment = Comment.of(board,request.getContents(),request.getWriter());
        commentRepository.save(comment);
        return CommentAddResponse.of(comment);
    }

    @Transactional
    public CommentReplyResponse reply(CommentReplyRequest request){
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        Comment findComment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));

        Comment comment = Comment.of(board,request.getContents(),request.getWriter(),findComment);
        commentRepository.save(comment);
        return CommentReplyResponse.of(comment);
    }

    public CommentChildListResponse findAllByParentId(CommentChildRequest request){
        return CommentChildListResponse.of(commentRepository.findAllByParentIdDesc(
                request.getCommentId(),
                PageAbleFactory.create(request.getPage(), 10,"Id",PageAbleFactory.DESC)
        ));
    }

    public List<CommentBoardResponse> findAllByBoardIdAndNullDesc(Long boardId){
        return commentRepository.findAllByBoardIdAndNullDesc(boardId).stream()
                .map(CommentBoardResponse::of)
                .toList();
    }

    @Transactional
    public void deleteByCommentId(Long commentId){
        commentRepository.deleteById(commentId);
    }

}
