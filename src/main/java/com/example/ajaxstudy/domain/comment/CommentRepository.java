package com.example.ajaxstudy.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    /**
     * 자식 객체를 먼저 삭제하고 그 후에 부모를 삭제해야 한다.
     * @param commentId
     */
    @Modifying
    @Query(value = "delete from Comment c where c.parent_id =:commentId",nativeQuery = true)
    public void deleteChildAllByCommentId(@Param("commentId")Long commentId);

    @Modifying
    @Query(value = "delete from Comment c where c.id =:commentId",nativeQuery = true)
    public void deleteById(@Param("commentId")Long commentId);

}
