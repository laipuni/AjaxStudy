package com.example.ajaxstudy.domain.comment.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentChildResponse {

    private Long commentId;
    private String writer;
    private String contents;
    private int childNum;

    @Builder
    @QueryProjection
    public CommentChildResponse(final Long commentId, final String writer, final String contents,final int childNum) {
        this.commentId = commentId;
        this.writer = writer;
        this.contents = contents;
        this.childNum = childNum;
    }

}
