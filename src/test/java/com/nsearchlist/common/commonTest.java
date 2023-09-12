package com.nsearchlist.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class commonTest {
    @Test
    public void commonTest() throws UnsupportedEncodingException {
        String org = "폐배터리, \"처리방법\"";
        String result = org.replaceAll("([\",])","\\\\$1");
        System.out.println(result);
    }
}