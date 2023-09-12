package com.nsearchlist.service.exposed;

import com.nsearchlist.domain.exposed.Exposed;
import com.nsearchlist.domain.exposed.ExposedRepository;
import com.nsearchlist.web.dto.exposed.ExposedListResponseDto;
import com.nsearchlist.web.dto.exposed.ExposedSaveRequestDto;
import com.nsearchlist.web.dto.exposed.ExposedUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExposedService {
    private final ExposedRepository exposedRepository;

    @Transactional
    public Long save(ExposedSaveRequestDto requestDto) {
        int cnt = exposedRepository.getCountByMemberIdx(requestDto.getMemberIdx());
        requestDto.setSort(cnt+1);
        return exposedRepository.save(requestDto.toEntity()).getIdx();
    }

    @Transactional(readOnly = true)
    public List<ExposedListResponseDto> findByMemberIdx(Long memberIdx) {
        return exposedRepository.getListByMemberIdx(memberIdx).stream()
                .map(ExposedListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long idx, ExposedUpdateRequestDto requestDto) {
        Exposed exposed = exposedRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 키워드가 없습니다"));

        exposed.update(requestDto.getSort());

        return idx;
    }

    @Transactional
    public void delete (Long idx) {
        Exposed exposed = exposedRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("해당 키워드가 없습니다"));
        Long memberIdx = exposed.getMemberIdx();
        int sort = exposed.getSort();
        exposedRepository.delete(exposed);
        exposedRepository.updateSort(memberIdx, sort);
    }
}
