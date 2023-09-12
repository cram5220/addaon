package com.nsearchlist.web;

import com.nsearchlist.config.auth.LoginUser;
import com.nsearchlist.config.auth.dto.SessionUser;
import com.nsearchlist.service.exposed.ExposedService;
import com.nsearchlist.service.keyword.KeywordService;
import com.nsearchlist.web.dto.exposed.ExposedSaveRequestDto;
import com.nsearchlist.web.dto.exposed.ExposedUpdateRequestDto;
import com.nsearchlist.web.dto.keyword.KeywordSaveRequestDto;
import com.nsearchlist.web.dto.keyword.KeywordUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ExposedController {

    private final ExposedService exposedService;

    @GetMapping("/exposed/list")
    public String list(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("exposedList",exposedService.findByMemberIdx(user.getId()));
        }

        return "customer/exposed/list";
    }

    // 키워드 등록
    @PostMapping("/exposed/save")
    public void save(@RequestBody ExposedSaveRequestDto requestDto, @LoginUser SessionUser user) {
        if (user != null) {
            requestDto.setMemberIdx(user.getId());
            exposedService.save(requestDto);
        }
    }

    // 키워드 수정 (순서 변경을 위해 필요)
    @PutMapping("/exposed/update/{idx}")
    public Long update(@PathVariable Long idx, @RequestBody ExposedUpdateRequestDto requestDto) {
        return exposedService.update(idx, requestDto);
    }

    // 키워드 삭제
    @DeleteMapping("/exposed/delete/{idx}")
    public Long delete(@PathVariable Long idx, @LoginUser SessionUser user) {
        if (user != null) {
            exposedService.delete(idx);
            return idx;
        }else {
            return 0L;
        }
    }

}
