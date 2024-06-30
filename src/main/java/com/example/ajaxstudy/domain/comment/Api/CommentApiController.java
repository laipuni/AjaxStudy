package com.example.ajaxstudy.domain.comment.Api;

import com.example.ajaxstudy.domain.ApiCollectionResponse;
import com.example.ajaxstudy.domain.comment.CommentService;
import com.example.ajaxstudy.domain.comment.request.CommentAddRequest;
import com.example.ajaxstudy.domain.comment.request.CommentChildRequest;
import com.example.ajaxstudy.domain.comment.request.CommentReplyRequest;
import com.example.ajaxstudy.domain.comment.response.CommentAddResponse;
import com.example.ajaxstudy.domain.comment.response.CommentChildResponse;
import com.example.ajaxstudy.domain.comment.response.CommentReplyResponse;
import com.example.ajaxstudy.domain.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/comment/{commentId}/reply")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiCollectionResponse<CommentChildResponse> getChild(@RequestBody CommentChildRequest request){
        List<CommentChildResponse> childs = commentService.findAllByParentId(request);
        return ApiCollectionResponse.of(HttpStatus.ACCEPTED,childs);
    }
}
