package com.nsearchlist.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//making this interface meaning crud method is created automatically
public interface PostsRepository extends JpaRepository<Posts, Long> {

    // JPA 로 제공되지 않는 것은 쿼리로 해결할 수 있고, 더 큰 경우 suerydsl(추천), jooq, myBatis 등 사용
    @Query("SELECT p FROM Posts p ORDER BY p.idx DESC")
    List<Posts> findAllDesc();
}
