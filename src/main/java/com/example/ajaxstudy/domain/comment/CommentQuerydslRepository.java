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

        boolean hasNext = hasNext(content.size(), pageable);
        if(hasNext){
            //마지막 데이터는 다음 데이터가 존재하는 여부를 위함이기 때문에 제거
            content.remove(content.size() - 1);
        }

        return new SliceImpl<>(content,pageable,hasNext);
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

        boolean hasNext = hasNext(content.size(), pageable);
        if(hasNext){
            //마지막 데이터는 다음 데이터가 존재하는 여부를 위함이기 때문에 제거
            content.remove(content.size() - 1);
        }

        return new SliceImpl<>(content,pageable,hasNext);
    }

    private boolean hasNext(final int contentSize,Pageable pageable) {
        //10개가 아닌 11개르 조회했을 때 11개면 다음 데이터가 있다고 판단
        return contentSize > pageable.getPageSize();
    }
}
