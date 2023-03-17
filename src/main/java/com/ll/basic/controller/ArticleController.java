package com.ll.basic.controller;

import com.ll.basic.domain.Article;
import com.ll.basic.response.StatusResponse;
import com.ll.basic.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/write")
    @ResponseBody
    public StatusResponse write(String title, String body) {
        Article article = Article
                .builder()
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .title(title)
                .body(body)
                .build();
        if (title == null || title.trim().length() == 0) {
            return new StatusResponse("F-1", "title(을)를 입력해주세요.");
        }

        if (body == null || body.trim().length() == 0) {
            return new StatusResponse("F-2", "body(을)를 입력해주세요.");
        }
        articleService.write(article);
        return new StatusResponse("S-1", "1번 글이 생성되었습니다.");
    }
}