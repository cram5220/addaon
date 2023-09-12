package com.nsearchlist.web.dto.keyword;

import com.nsearchlist.domain.keyword.Keyword;
import lombok.Getter;

@Getter
public class KeywordListResponseDto {
    private Long idx;
    private Long memberIdx;
    private int sort;
    private String keywordText;

    public KeywordListResponseDto(Keyword entity) {
        this.idx = entity.getIdx();
        this.memberIdx = entity.getMemberIdx();
        this.sort = entity.getSort();
        this.keywordText = entity.getKeywordText();
    }
}
