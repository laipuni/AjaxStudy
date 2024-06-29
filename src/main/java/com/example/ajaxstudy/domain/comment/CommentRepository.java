package com.example.ajaxstudy.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "select * from Comment c where c.parent =: parentId order by c.id desc",nativeQuery = true)
    public List<Comment> findAllByParentIdDesc(@Param("parentId") Long parentId);

}
