package com.example.ajaxstudy.domain.comment.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentChildListResponse {

    private int size;
    private int page;
    private boolean hasNext;
    private List<CommentChildResponse> childList;

    @Builder
    private CommentChildListResponse(final int size, final int page,final boolean hasNext, final List<CommentChildResponse> childList) {
        this.size = size;
        this.page = page;
        this.hasNext = hasNext;
        this.childList = childList;
    }

    public static CommentChildListResponse of(Slice<CommentChildResponse> comments){
        return CommentChildListResponse.builder()
                .size(comments.getNumberOfElements())
                .page(comments.getNumber())
                .hasNext(comments.hasNext())
                .childList(comments.getContent())
                .build();
    }
}
