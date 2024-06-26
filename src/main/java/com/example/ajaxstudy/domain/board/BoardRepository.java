package com.example.ajaxstudy.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "select * from Board b order by b.id desc ",nativeQuery = true)
    public List<Board> findAllOrderByBoardIdDesc();

}
