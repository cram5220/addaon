package com.nsearchlist.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        System.out.println("cleanup");
        postsRepository.deleteAll();
        System.out.println("done");
    }

    @Test
    public void 게시글저장불러오기() {
        //given
        String postTitle = "테스트 게시글";
        String postContent = "테스트 본문";

        postsRepository.save(Posts.builder()
                        .postTitle(postTitle)
                        .postContent(postContent)
                        .postAuthor("cram5220")
                        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getPostTitle()).isEqualTo(postTitle);
        assertThat(posts.getPostContent()).isEqualTo(postContent);

        System.out.println("test");

    }

    @Test
    public void BaseTimeEntity_등록() {

        //given
        LocalDateTime now = LocalDateTime.of(2022,3,7,0,0,0);
        postsRepository.save(Posts.builder()
                        .postTitle("title")
                        .postContent("content")
                        .postAuthor("author")
                        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts post = postsList.get(0);

        System.out.println(">>>>>>>>>>> CreatedDate : "+post.getCreatedDate()+", modifiedDate : "+post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);


    }
}
