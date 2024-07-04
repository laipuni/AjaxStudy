package com.example.ajaxstudy.domain.board;

import com.example.ajaxstudy.domain.comment.Comment;
import com.example.ajaxstudy.domain.heart.Heart;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Board {
    public static final int HEART_INIT = 0;

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private int heartNum;

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    private List<Heart> hearts = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Board(final String title,final String writer,final String contents, final int heartNum) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.heartNum = heartNum;
    }

    public static Board of(final String title,final String writer,final String contents){
        return Board.builder()
                .title(title)
                .writer(writer)
                .contents(contents)
                .heartNum(HEART_INIT)
                .build();
    }

    public void addHeart(final Heart heart) {
        hearts.add(heart);
        incrementHeartNum();
    }

    public void addComment(final Comment comment){
        comments.add(comment);
    }

    public void incrementHeartNum() {
        this.heartNum += 1;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getWriter() {
        return writer;
    }

    public int getHeartNum() {
        return heartNum;
    }
}
