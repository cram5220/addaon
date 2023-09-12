package com.nsearchlist.web.dto.searchResult;

import com.nsearchlist.domain.searchResult.SearchResult;
import lombok.Getter;

@Getter
public class SearchResultResponseDto {
    private Long idx;
    private String keyword;
    private Long retCnt;
    private Long pcView;
    private Long mView;
    private String sections;
    private String posts;

    public SearchResultResponseDto(SearchResult entity) {
        this.idx = entity.getIdx();
        this.keyword = entity.getKeyword();
        this.retCnt = entity.getRetCnt();
        this.pcView = entity.getPcView();
        this.mView = entity.getMView();
        this.sections = entity.getSections();
        this.posts = entity.getPosts();
    }
}
