package com.nsearchlist.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); //소셜 로그인으로 반환되는 값 중 email 을 통해 기존 사용자인지 판단
}
