package com.nsearchlist.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsearchlist.domain.keyword.Keyword;
import com.nsearchlist.domain.keyword.KeywordRepository;
import com.nsearchlist.web.dto.keyword.KeywordSaveRequestDto;
import com.nsearchlist.web.dto.keyword.KeywordUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KeywordApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    private void tearDown() throws Exception {
        keywordRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void save() throws Exception {
        //given
        Long memberIdx = 1L;
        String keywordText = "testKeyword";

        KeywordSaveRequestDto requestDto = KeywordSaveRequestDto.builder()
                .memberIdx(memberIdx)
                .keywordText(keywordText)
                .build();

        String url = "http://localhost:"+port+"/keyword/save";

        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Keyword> all = keywordRepository.findAll();
        assertThat(all.get(0).getMemberIdx()).isEqualTo(memberIdx);
        assertThat(all.get(0).getKeywordText()).isEqualTo(keywordText);

    }


    @Test
    @WithMockUser(roles = "USER")
    public void update() throws Exception {
        //given
        Keyword savedKeyword = keywordRepository.save(Keyword.builder()
                .memberIdx(1L)
                .keywordText("keywordText")
                .build());

        Long updateIdx = savedKeyword.getIdx();
        int expectedSort = 2;

        KeywordUpdateRequestDto requestDto = KeywordUpdateRequestDto.builder()
                .sort(2)
                .build();

        String url = "http://localhost:"+port+"/keyword/update/"+updateIdx;

       //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Keyword> all = keywordRepository.findAll();
        assertThat(all.get(0).getIdx()).isEqualTo(updateIdx);
        assertThat(all.get(0).getSort()).isEqualTo(expectedSort);

    }

}
