package com.nsearchlist.web.dto.exposed;

import com.nsearchlist.domain.exposed.Exposed;
import lombok.Getter;

@Getter
public class ExposedListResponseDto {
    private Long idx;
    private Long memberIdx;
    private int sort;
    private String keyword;
    private String title;
    private String url;

    public ExposedListResponseDto(Exposed entity) {
        this.idx = entity.getIdx();
        this.memberIdx = entity.getMemberIdx();
        this.sort = entity.getSort();
        this.keyword = entity.getKeyword();
        this.title = entity.getTitle();
        this.url = entity.getUrl();
    }
}
