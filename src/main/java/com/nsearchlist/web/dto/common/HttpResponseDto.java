package com.nsearchlist.web.dto.common;
import lombok.Getter;

@Getter
public class HttpResponseDto {
    private String responseText;
    public HttpResponseDto(String responseText){
        this.responseText = responseText;
    }
}
