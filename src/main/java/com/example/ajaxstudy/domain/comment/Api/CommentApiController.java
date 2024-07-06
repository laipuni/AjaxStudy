package com.example.ajaxstudy.domain.comment.Api;

import com.example.ajaxstudy.domain.comment.CommentService;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentChildRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;
import com.example.ajaxstudy.domain.comment.response.CommentAddResponse;
import com.example.ajaxstudy.domain.comment.response.CommentChildListResponse;
import com.example.ajaxstudy.domain.comment.response.CommentReplyResponse;
import com.example.ajaxstudy.domain.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

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

    @GetMapping("/comment/reply")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CommentChildListResponse> getChild(@Valid @ModelAttribute CommentChildRequest request){
        CommentChildListResponse children = commentService.findAllByParentId(request);
        return ApiResponse.of(HttpStatus.OK,children);
    }

    @DeleteMapping("/comment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse<Object> deleteComment(@RequestBody Long commentId){
        commentService.deleteByCommentId(commentId);
        return ApiResponse.of(HttpStatus.ACCEPTED,null);
    }
}
