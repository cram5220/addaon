package com.nsearchlist.web.dto.posts;

import com.nsearchlist.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long idx;
    private String postTitle;
    private String postContent;
    private String postAuthor;

    public PostsResponseDto (Posts entity) {
        this.idx = entity.getIdx();
        this.postTitle = entity.getPostTitle();
        this.postContent = entity.getPostContent();
        this.postAuthor = entity.getPostAuthor();
    }
}
