package com.nsearchlist.web.dto.posts;

import com.nsearchlist.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long idx;
    private String postTitle;
    private String postAuthor;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.idx = entity.getIdx();
        this.postTitle = entity.getPostTitle();
        this.postAuthor = entity.getPostAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
