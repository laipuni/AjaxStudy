package com.example.ajaxstudy.domain.comment.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentBoardListResponse {

    private int size;
    private int page;
    private boolean hasNext;
    private List<CommentBoardResponse> comments;

    @Builder
    private CommentBoardListResponse(final int size, final int page, final boolean hasNext, final List<CommentBoardResponse> comments) {
        this.size = size;
        this.page = page;
        this.hasNext = hasNext;
        this.comments = comments;
    }

    public static CommentBoardListResponse of(Slice<CommentBoardResponse> contents){
        return CommentBoardListResponse.builder()
                .size(contents.getNumberOfElements())
                .page(contents.getNumber())
                .hasNext(contents.hasNext())
                .comments(contents.getContent())
                .build();
    }
}
