package com.nsearchlist.web;

import com.nsearchlist.config.auth.LoginUser;
import com.nsearchlist.config.auth.dto.SessionUser;
import com.nsearchlist.service.keyword.KeywordService;
import com.nsearchlist.service.posts.PostsService;
import com.nsearchlist.web.dto.keyword.KeywordSaveRequestDto;
import com.nsearchlist.web.dto.keyword.KeywordUpdateRequestDto;
import com.nsearchlist.web.dto.posts.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/keyword/list")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("keywordList",keywordService.findByMemberIdx(user.getId()));
        }

        return "customer/keyword/list";
    }

    // 키워드 등록
    @PostMapping("/keyword/save")
    public void save(@RequestBody KeywordSaveRequestDto requestDto, @LoginUser SessionUser user) {
        if (user != null) {
            requestDto.setMemberIdx(user.getId());
            keywordService.save(requestDto);
        }
    }

    // 키워드 수정 (순서 변경을 위해 필요)
    @PutMapping("/keyword/update/{idx}")
    public Long update(@PathVariable Long idx, @RequestBody KeywordUpdateRequestDto requestDto) {
        return keywordService.update(idx, requestDto);
    }

    // 키워드 삭제
    @DeleteMapping("/keyword/delete/{idx}")
    public void delete(@PathVariable Long idx, @LoginUser SessionUser user) {
        if (user != null) {
            keywordService.delete(idx);
        }
    }

}
