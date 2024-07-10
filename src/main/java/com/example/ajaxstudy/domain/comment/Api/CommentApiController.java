package com.example.ajaxstudy.domain.comment.Api;

import com.example.ajaxstudy.domain.ApiResponse;
import com.example.ajaxstudy.domain.comment.CommentService;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentModifyRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;
import com.example.ajaxstudy.domain.comment.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @GetMapping("/comment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<CommentBoardListResponse> getComment(@RequestParam("boardId")Long boardId, @RequestParam("page")int page){
        CommentBoardListResponse comments = commentService.findAllByBoardIdAndNullDesc(boardId, page);
        return ApiResponse.of(HttpStatus.ACCEPTED,comments);
    }

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CommentAddResponse> addComment(@Valid @RequestBody CommentAddRequest request){
        CommentAddResponse response = commentService.record(request);
        return ApiResponse.of(HttpStatus.CREATED,response);
    }

    @PostMapping("/comment/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CommentReplyResponse> addReply(@Valid @RequestBody CommentReplyRequest request){
        CommentReplyResponse response = commentService.reply(request);
        return ApiResponse.of(HttpStatus.CREATED,response);
    }

    @PutMapping("/comment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<CommentModifyResponse> modifyComment(@Valid @RequestBody CommentModifyRequest request){
        return ApiResponse.of(HttpStatus.ACCEPTED,commentService.modifyComment(request));
    }

    @GetMapping("/comment/reply")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CommentChildListResponse> getChild(@RequestParam Long commentId, @RequestParam int page){
        CommentChildListResponse children = commentService.findAllByParentId(commentId,page);
        return ApiResponse.of(HttpStatus.OK,children);
    }

    @DeleteMapping("/comment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Object> deleteComment(@RequestBody Long commentId){
        commentService.deleteByCommentId(commentId);
        return ApiResponse.of(HttpStatus.ACCEPTED,null);
    }
}
