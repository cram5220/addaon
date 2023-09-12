package com.nsearchlist.web;

import com.nsearchlist.config.auth.LoginUser;
import com.nsearchlist.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "index";
    }

    @GetMapping("/admin")
    public String adminIndex(Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "admin/index";
    }

}

