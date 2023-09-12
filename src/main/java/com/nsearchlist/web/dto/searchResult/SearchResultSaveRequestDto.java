package com.nsearchlist.web.dto.searchResult;

import com.nsearchlist.domain.searchResult.SearchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchResultSaveRequestDto {
    private String keyword;
    private Long pcView;
    private Long mView;
    private String sections;
    private String posts;

    @Builder
    public SearchResultSaveRequestDto(String keyword, Long pcView, Long mView, String sections, String posts) {
        this.keyword = keyword;
        this.pcView = pcView;
        this.mView = mView;
        this.sections = sections;
        this.posts = posts;
    }

    public SearchResult toEntity() {
        return SearchResult.builder()
                .keyword(keyword)
                .pcView(pcView)
                .mView(mView)
                .sections(sections)
                .posts(posts)
                .build();
    }
}
