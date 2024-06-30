package com.example.ajaxstudy.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "select c from Comment as c where c.parent.id =:parentId order by c.id desc")
    public List<Comment> findAllByParentIdDesc(@Param("parentId") Long parentId);

}
