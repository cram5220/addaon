package com.nsearchlist.service.searchResult;

import com.nsearchlist.domain.searchResult.SearchResult;
import com.nsearchlist.domain.searchResult.SearchResultRepository;
import com.nsearchlist.web.dto.searchResult.SearchResultResponseDto;
import com.nsearchlist.web.dto.searchResult.SearchResultSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SearchResultService {
    private final SearchResultRepository srRepository;

    // 키워드 검색결과 조회
    @Transactional(readOnly = true)
    public SearchResultResponseDto findByKeyword(String keyword){
        
        SearchResult entity = srRepository.findByKeyword(keyword); //업데이트 시간이 1시간 이내인게 db에 없으면
        if(entity == null) {
            NaverResultGetter nrg = new NaverResultGetter(keyword);
            entity = new SearchResult(keyword,nrg.getPcView(), nrg.getMobileView(), nrg.getSectionTitles(), nrg.getPostsJson());
            srRepository.save(entity); // 있는데 또 save 할 수 있음 이거 고쳐야함 save or update 로
        }else if (entity.getModifiedDate().isBefore(LocalDateTime.now().minusHours(1))){ //1시간 전 보다 예전에 업뎃된 내용이면
            update(entity, keyword);
        }
        entity.incRetCnt(); // 조회수 +1 한 뒤
        return new SearchResultResponseDto(entity);//반환
    }

    // 키워드 검색결과 저장
    @Transactional
    public Long save(SearchResultSaveRequestDto requestDto){
        return srRepository.save(requestDto.toEntity()).getIdx();
    }

    // 키워드 검색결과 삭제
    @Transactional
    public void delete(String keyword) {
        SearchResult entity = srRepository.findByKeyword(keyword);
        srRepository.delete(entity);
    }

    // 키워드 검색결과 수정
    @Transactional
    public void update(SearchResult entity, String keyword) { //
        //SearchResult entity = srRepository.findByKeyword(keyword);
        NaverResultGetter nrg = new NaverResultGetter(keyword);
        entity.update(nrg.getPcView(), nrg.getMobileView(), nrg.getSectionTitles(), nrg.getPostsJson());
    }
    
}
