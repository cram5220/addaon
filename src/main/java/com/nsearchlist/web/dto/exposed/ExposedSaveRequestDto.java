package com.nsearchlist.web.dto.exposed;

import com.nsearchlist.domain.exposed.Exposed;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExposedSaveRequestDto {

    private Long idx;
    private Long memberIdx;
    private int sort;
    private String keyword;
    private String title;
    private String url;

    @Builder
    public ExposedSaveRequestDto(Long memberIdx, String keyword, String title, String url) {
        this.memberIdx = memberIdx;
        this.keyword = keyword;
        this.title = title;
        this.url = url;
    }

    public Exposed toEntity() {
        return Exposed.builder()
                .memberIdx(memberIdx)
                .keyword(keyword)
                .title(title)
                .url(url)
                .sort(sort)
                .build();
    }

}
