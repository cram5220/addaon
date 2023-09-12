package com.nsearchlist.domain.keyword;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KeywordRepositoryTest {
    @Autowired
    KeywordRepository keywordRepository;

    @AfterEach
    public void cleanup() {
        keywordRepository.deleteAll();
    }

    @Test
    public void getListByMemberIdx() {
        //given
        Long memberIdx = 1L;

        keywordRepository.save(Keyword.builder()
                        .memberIdx(memberIdx)
                        .keywordText("python")
                .build());

        //when
        List<Keyword> keywordList = keywordRepository.getListByMemberIdx(memberIdx);

        //then
        Keyword keyword = keywordList.get(0);
        assertThat(keyword.getMemberIdx()).isEqualTo(memberIdx);
        assertThat(keyword.getKeywordText()).isEqualTo("python");

    }

    @Test
    public void getKeywordCountByMemberIdx() {
        //given
        Long memberIdx = 1L;

        keywordRepository.save(Keyword.builder()
                .memberIdx(memberIdx)
                .keywordText("python")
                .build());

        keywordRepository.save(Keyword.builder()
                .memberIdx(memberIdx)
                .keywordText("python2")
                .build());

        //when
        int cnt = keywordRepository.getKeywordCountByMemberIdx(memberIdx);

        //then
        assertThat(cnt).isEqualTo(2);

    }


    @Test
    public void updateSort() {
        //given
        Long memberIdx = 1L;

        keywordRepository.save(Keyword.builder()
                .memberIdx(memberIdx)
                .keywordText("python")
                .sort(1)
                .build());

        keywordRepository.save(Keyword.builder()
                .memberIdx(memberIdx)
                .keywordText("python2")
                .sort(2)
                .build());

        keywordRepository.save(Keyword.builder()
                .memberIdx(memberIdx)
                .keywordText("python3")
                .sort(3)
                .build());

        keywordRepository.save(Keyword.builder()
                .memberIdx(memberIdx)
                .keywordText("python4")
                .sort(4)
                .build());

        List<Keyword> list = keywordRepository.findAll();
        System.out.println(list.get(1).getKeywordText());
        keywordRepository.delete(list.get(1));
        keywordRepository.updateSort(memberIdx,2);

        //when
        List<Keyword> keywordList = keywordRepository.getListByMemberIdx(memberIdx);
//        for(Keyword k : keywordList) {
//            System.out.println("=================================================================");
//            System.out.println(Integer.toString(k.getSort())+" "+k.getKeywordText());
//        }

        //then
        assertThat(keywordList.get(0).getSort()).isEqualTo(1);
        assertThat(keywordList.get(0).getKeywordText()).isEqualTo("python");
        assertThat(keywordList.get(1).getSort()).isEqualTo(2);
        assertThat(keywordList.get(1).getKeywordText()).isEqualTo("python3");
        assertThat(keywordList.get(2).getSort()).isEqualTo(3);
        assertThat(keywordList.get(2).getKeywordText()).isEqualTo("python4");

    }


}
