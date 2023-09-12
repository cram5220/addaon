package com.nsearchlist.web.dto.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String postTitle;
    private String postContent;

    @Builder
    public PostsUpdateRequestDto(String postTitle, String postContent){
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
}
