package com.nsearchlist.web.dto.posts;

import com.nsearchlist.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String postTitle;
    private String postContent;
    private String postAuthor;

    @Builder
    public PostsSaveRequestDto(String postTitle, String postContent, String postAuthor) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
    }

    public Posts toEntity() {
        return Posts.builder()
                .postTitle(postTitle)
                .postContent(postContent)
                .postAuthor(postAuthor)
                .build();
    }

}
