package com.puri12.spring1;

import com.puri12.spring1.entity.Category;
import com.puri12.spring1.entity.Post;
import com.puri12.spring1.repository.PostRepository;
import com.puri12.spring1.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
public class Spring1Application {

    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(Spring1Application.class, args);
    }

    // Week02Application.java 의 main 함수 아래에 붙여주세요.
    @Bean
    public CommandLineRunner demo(PostRepository postRepository, PostService postService) {
        return (args) -> {
            int num = 0;

            for (int i = 0; i < 20; i++) {
                num = (int) (Math.random()*19) + 1;
                postRepository.save(new Post("test", "title"+i, "contents" + num , Category.findById(num), passwordEncoder.encode("passwd")));
            }
        };
    }


}
