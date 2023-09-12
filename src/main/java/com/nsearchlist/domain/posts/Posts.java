package com.nsearchlist.domain.posts;

import com.nsearchlist.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 500, nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContent;

    private String postAuthor;

    @Builder
    public Posts(String postTitle, String postContent, String postAuthor) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
    }

    public void update(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

}
