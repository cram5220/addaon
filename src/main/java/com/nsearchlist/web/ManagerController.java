package com.nsearchlist.web;

import com.nsearchlist.config.auth.LoginUser;
import com.nsearchlist.config.auth.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController {
    @GetMapping("/admin/manager")
    public String adminIndex(Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        return "admin/manager/list";
    }
}
