package com.puri12.spring1;

import com.puri12.spring1.entity.Post;
import com.puri12.spring1.repository.PostRepository;
import com.puri12.spring1.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Spring1Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring1Application.class, args);

    }

    // Week02Application.java 의 main 함수 아래에 붙여주세요.
    @Bean
    public CommandLineRunner demo(PostRepository postRepository, PostService postService) {
        return (args) -> {

            for (int i = 0; i < 100; i++)
                postRepository.save(new Post("test", "tutle"+i, "contents", "passwd"));
        };
    }


}
