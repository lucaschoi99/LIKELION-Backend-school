package com.ll.basic.config;

import com.ll.basic.domain.Article;
import com.ll.basic.service.ArticleService;
import com.ll.basic.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
// NotProd : 개발환경이 이거나, 테스트환경일 때만 실행
public class NotProd {
    @Bean
    public CommandLineRunner initData(UserService userService, ArticleService articleService) {
        return args -> {
            // 이 부분은 스프링부트 앱이 실행되고, 본격적으로 작동하기 전에 실행된다.
            userService.addUsers();

            Article article = new Article();
            article.setTitle("글1");
            article.setBody("내용1");

            Article article2 = new Article();
            article2.setTitle("글2");
            article2.setBody("내용2");

            articleService.write(article);
            articleService.write(article2);
        };
    }
}