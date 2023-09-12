package com.nsearchlist.service.searchResult;

import com.nsearchlist.domain.searchResult.SearchResult;
import com.nsearchlist.domain.searchResult.SearchResultRepository;
import com.nsearchlist.web.dto.searchResult.SearchResultResponseDto;
import com.nsearchlist.web.dto.searchResult.SearchResultSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SearchResultServiceTest {

    @Autowired
    SearchResultRepository srRepository;

    @Autowired
    SearchResultService searchResultService;

    @AfterEach
    public void clear() {
        srRepository.deleteAll();
    }

    @Test
    public void saveTest() {

        //given
        NaverResultGetter nrg = new NaverResultGetter("메이플");
        SearchResultSaveRequestDto requestDto = SearchResultSaveRequestDto.builder()
                .keyword(nrg.getKeyword())
                .pcView(nrg.getPcView())
                .mView(nrg.getMobileView())
                .sections(nrg.getSectionTitles())
                .posts(nrg.getPostsJson())
                .build();

        searchResultService.save(requestDto);

        //when
        SearchResult sr = srRepository.findAll().get(0);

        //then
        assertThat(sr.getKeyword()).isEqualTo(nrg.getKeyword());
        System.out.println(sr.getRetCnt());
        System.out.println(sr.getPcView()); //이거 왜 null 임
        System.out.println(sr.getSections());
        System.out.println(sr.getPosts());

    }

    @Test
    public void findByKeywordTest(){
        //given
        String keyword = "메이플";
        //when
        SearchResultResponseDto responseDto = searchResultService.findByKeyword(keyword);
        //then
        assertThat(responseDto.getKeyword()).isEqualTo(keyword);
        System.out.println(responseDto.getSections());

    }

    @Test
    public void deleteTest() {
        //given
        String keyword = "메이플";
        NaverResultGetter nrg = new NaverResultGetter(keyword);
        SearchResultSaveRequestDto requestDto = SearchResultSaveRequestDto.builder()
                .keyword(nrg.getKeyword())
                .pcView(nrg.getPcView())
                .mView(nrg.getMobileView())
                .sections(nrg.getSectionTitles())
                .posts(nrg.getPostsJson())
                .build();

        searchResultService.save(requestDto);
        searchResultService.delete(keyword);

        //when
        //SearchResultResponseDto responseDto = searchResultService.findByKeyword(keyword);

        //then
        assertThat(srRepository.findAll().size()).isEqualTo(0);
    }

//    @Test
//    public void updateTest() throws InterruptedException {
//        //given
//        String keyword = "메이플";
//
//        NaverResultGetter nrg = new NaverResultGetter("메이플");
//        SearchResultSaveRequestDto requestDto = SearchResultSaveRequestDto.builder()
//                .keyword(nrg.getKeyword())
//                .pcView(nrg.getPcView())
//                .mView(nrg.getMobileView())
//                .sections(nrg.getSectionTitles())
//                .posts(nrg.getPostsJson())
//                .build();
//
//        searchResultService.save(requestDto);
//
//        //when
//        SearchResult sr = srRepository.findAll().get(0);
//        System.out.println(sr.getModifiedDate());
//        Thread.sleep(3000);
//        //then
//        searchResultService.update(keyword);
//        sr = srRepository.findAll().get(0);
//        System.out.println(sr.getModifiedDate());
//    }
}
