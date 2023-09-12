package com.nsearchlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 메인클래스, 최상단 위치
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}

//h2 console jdbc url = jdbc:h2:~/test 였는데 jdbc:h2:mem:testdb 로 바꿈