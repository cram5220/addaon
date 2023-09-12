package com.nsearchlist.web;

import com.nsearchlist.service.searchResult.SearchResultService;
import com.nsearchlist.web.dto.searchResult.SearchResultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchResultController {
    private final SearchResultService searchResultService;

    //검색결과 조회
    @GetMapping("/api/searchResult/{keyword}")
    public SearchResultResponseDto findByKeyword(@PathVariable String keyword){
       return searchResultService.findByKeyword(keyword);
    }

}
