package com.nsearchlist.web;

import com.nsearchlist.common.HttpGetResponse;
import com.nsearchlist.service.searchResult.SearchResultService;
import com.nsearchlist.web.dto.common.HttpResponseDto;
import com.nsearchlist.web.dto.searchResult.SearchResultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommonController {
    private final SearchResultService searchResultService;

    //검색결과 조회
    @GetMapping("/cmn/blogVisitCnt/{blogId}")
    public HttpResponseDto blogVisitCnt(@PathVariable String blogId){
        String url = "https://m.blog.naver.com/"+blogId;
        HttpGetResponse res = new HttpGetResponse(url);
        HttpResponseDto responseDto = new HttpResponseDto(res.getResponseText());

        return responseDto;
    }

}
