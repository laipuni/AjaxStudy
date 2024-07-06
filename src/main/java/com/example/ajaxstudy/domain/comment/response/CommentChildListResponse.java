package com.example.ajaxstudy.domain.comment.response;

import com.example.ajaxstudy.domain.comment.Comment;
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
    private List<CommentChildResponse> childList;

    @Builder
    private CommentChildListResponse(final int size, final int page, final List<CommentChildResponse> childList) {
        this.size = size;
        this.page = page;
        this.childList = childList;
    }

    public static CommentChildListResponse of(Slice<Comment> comments){
        return CommentChildListResponse.builder()
                .size(comments.getNumberOfElements())
                .page(comments.getNumber())
                .childList(
                        comments.getContent().stream()
                                .map(CommentChildResponse::of)
                                .toList()
                )
                .build();
    }
}
