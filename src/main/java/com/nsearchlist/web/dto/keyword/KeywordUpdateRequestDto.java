package com.nsearchlist.web.dto.keyword;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordUpdateRequestDto {
    private int sort;

    @Builder
    public KeywordUpdateRequestDto(int sort){
        this.sort = sort;
    }
}
