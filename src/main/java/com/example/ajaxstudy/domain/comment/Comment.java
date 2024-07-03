package com.example.ajaxstudy.domain.comment;

import com.example.ajaxstudy.domain.board.Board;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childs = new ArrayList<>();

    @Builder
    private Comment(final Board board,final String contents, final String writer) {
        this.board = board;
        this.contents = contents;
        this.writer = writer;
    }

    public static Comment of(final Board board,final String contents,final String writer){
        Comment comment = Comment.builder()
                .board(board)
                .writer(writer)
                .contents(contents)
                .build();
        board.addComment(comment);
        return comment;
    }

    public static Comment of(final Board board, final String contents, final String writer , final Comment parent){
        Comment comment = Comment.builder()
                .board(board)
                .writer(writer)
                .contents(contents)
                .build();
        comment.changeParentComment(parent);
        parent.addChildComment(comment);
        board.addComment(comment);
        return comment;
    }

    public void changeParentComment(Comment parent){
        this.parent = parent;
    }
    public void addChildComment(Comment child){
        childs.add(child);
    }
}
