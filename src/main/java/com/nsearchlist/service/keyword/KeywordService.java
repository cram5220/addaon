package com.nsearchlist.service.keyword;

import com.nsearchlist.domain.keyword.Keyword;
import com.nsearchlist.domain.keyword.KeywordRepository;
import com.nsearchlist.web.dto.keyword.KeywordListResponseDto;
import com.nsearchlist.web.dto.keyword.KeywordSaveRequestDto;
import com.nsearchlist.web.dto.keyword.KeywordUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public void save(KeywordSaveRequestDto requestDto) {
        int cnt = keywordRepository.getKeywordCountByMemberIdx(requestDto.getMemberIdx());
        requestDto.setSort(cnt+1);
        keywordRepository.save(requestDto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<KeywordListResponseDto> findByMemberIdx(Long memberIdx) {
        return keywordRepository.getListByMemberIdx(memberIdx).stream()
                .map(KeywordListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long idx, KeywordUpdateRequestDto requestDto) {
        Keyword keyword = keywordRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 키워드가 없습니다"));

        keyword.update(requestDto.getSort());

        return idx;
    }

    @Transactional
    public void delete (Long idx) {
        Keyword keyword = keywordRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("해당 키워드가 없습니다"));
        Long memberIdx = keyword.getMemberIdx();
        int sort = keyword.getSort();
        keywordRepository.delete(keyword);
        keywordRepository.updateSort(memberIdx, sort);
    }
}
