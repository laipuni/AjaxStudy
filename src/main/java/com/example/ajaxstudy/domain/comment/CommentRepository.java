package com.example.ajaxstudy.domain.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    @Query(value = "select c from Comment as c where c.parent.id =:parentId")
    public Slice<Comment> findAllByParentIdDesc(@Param("parentId") Long parentId, Pageable pageable);

    @Query(value = "select c from Comment as c where c.parent is null and c.board.id =:boardId order by c.id desc")
    public List<Comment> findAllByBoardIdAndNullDesc(@Param("boardId")Long boardId);

}
