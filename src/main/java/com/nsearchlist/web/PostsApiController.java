package com.nsearchlist.web;

import com.nsearchlist.service.posts.PostsService;
import com.nsearchlist.web.dto.posts.PostsResponseDto;
import com.nsearchlist.web.dto.posts.PostsSaveRequestDto;
import com.nsearchlist.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{idx}")
    public Long update(@PathVariable Long idx, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(idx, requestDto);
    }

    @GetMapping("/api/v1/posts/{idx}")
    public PostsResponseDto findById (@PathVariable Long idx) {
        return postsService.findById(idx);
    }

    @DeleteMapping("/api/v1/posts/{idx}")
    public Long delete(@PathVariable Long idx) {
        postsService.delete(idx);
        return idx;
    }


}
