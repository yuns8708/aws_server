// Comment.class
package com.example.postmanandwomen.entity;

import com.example.postmanandwomen.dto.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_board_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_account_id")
    private Account account;

    @Lob
    private String comment;

    public Comment(Post post, String comment, Account account) {
        this.comment = comment;
        this.post = post;
        this.account = account;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}