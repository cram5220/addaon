package com.nsearchlist.web;

import com.nsearchlist.config.auth.LoginUser;
import com.nsearchlist.config.auth.dto.SessionUser;
import com.nsearchlist.service.posts.PostsService;
import com.nsearchlist.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/post/list")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts",postsService.findAllDesc());

        System.out.println(user);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index_sample";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{idx}")
    public String postsUpdate(@PathVariable Long idx, Model model) {
        PostsResponseDto dto = postsService.findById(idx);
        model.addAttribute("post",dto);
        return "posts-update";
    }

}
