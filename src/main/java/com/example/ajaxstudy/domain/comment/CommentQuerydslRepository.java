package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.comment.response.CommentBoardResponse;
import com.example.ajaxstudy.domain.comment.response.CommentChildResponse;
import com.example.ajaxstudy.domain.comment.response.QCommentBoardResponse;
import com.example.ajaxstudy.domain.comment.response.QCommentChildResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.ajaxstudy.domain.comment.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<CommentChildResponse> findAllByParentIdDesc(Long commentId, Pageable pageable){
        List<CommentChildResponse> content = queryFactory.select(
                        new QCommentChildResponse(
                                comment.id,
                                comment.writer,
                                comment.contents,
                                comment.childs.size()
                        )
                )
                .from(comment)
                .where(comment.parent.id.eq(commentId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(comment.id.desc())
                .fetch();

        return new SliceImpl<>(content,pageable,hasNext(content.size(), pageable));
    }

    public Slice<CommentBoardResponse> findAllByBoardIdAndNullDesc(Long boardId, Pageable pageable){

        List<CommentBoardResponse> content = queryFactory.select(
                        new QCommentBoardResponse(
                                comment.id,
                                comment.writer,
                                comment.contents,
                                comment.childs.size()
                        )
                )
                .from(comment)
                .where(
                        comment.parent.isNull(),
                        comment.board.id.eq(boardId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(comment.id.desc())
                .fetch();

        return new SliceImpl<>(content,pageable,hasNext(content.size(), pageable));
    }

    private boolean hasNext(final int contentSize,Pageable pageable) {
        return contentSize > pageable.getPageSize();
    }
}
