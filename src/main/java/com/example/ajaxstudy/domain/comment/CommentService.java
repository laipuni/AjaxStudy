package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.PageAbleFactory;
import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentBoardRequest;
import com.example.ajaxstudy.domain.comment.request.CommentChildRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;
import com.example.ajaxstudy.domain.comment.response.CommentAddResponse;
import com.example.ajaxstudy.domain.comment.response.CommentBoardListResponse;
import com.example.ajaxstudy.domain.comment.response.CommentChildListResponse;
import com.example.ajaxstudy.domain.comment.response.CommentReplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentQuerydslRepository commentQuerydslRepository;

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
        return CommentChildListResponse.of(commentQuerydslRepository.findAllByParentIdDesc(
                request.getCommentId(),
                PageAbleFactory.create(request.getPage(), 10)
        ));
    }

    public CommentBoardListResponse findAllByBoardIdAndNullDesc(CommentBoardRequest request){
        return CommentBoardListResponse.of(commentQuerydslRepository.findAllByBoardIdAndNullDesc(
                request.getBoardId(),
                PageAbleFactory.create(request.getPage(),10)
        ));
    }

    @Transactional
    public void deleteByCommentId(Long commentId){
        commentRepository.deleteById(commentId);
    }

}
