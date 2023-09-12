package com.nsearchlist.web.dto.keyword;

import com.nsearchlist.domain.keyword.Keyword;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeywordSaveRequestDto {

    private Long memberIdx;
    private String keywordText;
    private int sort;

    @Builder
    public KeywordSaveRequestDto(Long memberIdx, String keywordText) {
        this.memberIdx = memberIdx;
        this.keywordText = keywordText;
        this.sort = sort;
    }

    public Keyword toEntity() {
        return Keyword.builder()
                .memberIdx(memberIdx)
                .keywordText(keywordText)
                .sort(sort)
                .build();
    }

}
