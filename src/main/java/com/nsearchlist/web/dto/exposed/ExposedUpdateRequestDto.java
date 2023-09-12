package com.nsearchlist.web.dto.exposed;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExposedUpdateRequestDto {
    private int sort;

    @Builder
    public ExposedUpdateRequestDto(int sort){
        this.sort = sort;
    }
}
