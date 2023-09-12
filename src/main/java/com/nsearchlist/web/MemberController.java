package com.nsearchlist.web;

import com.nsearchlist.config.auth.LoginUser;
import com.nsearchlist.config.auth.dto.SessionUser;
import com.nsearchlist.service.member.MemberService;
import com.nsearchlist.service.posts.PostsService;
import com.nsearchlist.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    // 내 회원 정보 조회
    @GetMapping("/member/info")
    public String info(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "customer/member/info";
    }

    // 내 결제 내역 조회
    @GetMapping("/member/payHis")
    public String payHis(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "customer/member/payHis";
    }

    // 내 결제 내역 조회
    @GetMapping("/member/asking")
    public String asking(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "customer/member/asking";
    }
}
