package com.nsearchlist.service.member;

import com.nsearchlist.domain.posts.Posts;
import com.nsearchlist.domain.posts.PostsRepository;
import com.nsearchlist.domain.user.UserRepository;
import com.nsearchlist.web.dto.posts.PostsListResponseDto;
import com.nsearchlist.web.dto.posts.PostsResponseDto;
import com.nsearchlist.web.dto.posts.PostsSaveRequestDto;
import com.nsearchlist.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final UserRepository userRepository;

//    @Transactional
//    public Long save(PostsSaveRequestDto requestDto) { //@NotNull 넣어도 되던데 의미 찾아보기
//        return postsRepository.save(requestDto.toEntity()).getIdx();
//    }
//
//    @Transactional
//    public Long update(Long idx, PostsUpdateRequestDto requestDto) {
//        Posts posts = postsRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 idx = "+idx));
//
//        posts.update(requestDto.getPostTitle(), requestDto.getPostContent());
//
//        return idx;
//    }
//
//    public PostsResponseDto findById (Long idx) {
//        Posts entity = postsRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 idx = "+idx));
//        return new PostsResponseDto(entity);
//    }
//
//    @Transactional(readOnly = true) //트랜잭션 범위는 유지하되 조회 기능만 남겨 조회 속도 개선시키기. 등록, 수정, 삭제기능이 전혀 없는 서비스 메소드에서 사용
//    public List<PostsListResponseDto> findAllDesc() {
//        return postsRepository.findAllDesc().stream()
//                .map(PostsListResponseDto::new) // .map(posts -> new PostsListResponseDto(posts)) 와 같음
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public void delete (Long idx) {
//        Posts post = postsRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다 idx = "+idx));
//        postsRepository.delete(post);
//    }


}
