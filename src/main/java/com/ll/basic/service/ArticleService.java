package com.ll.basic.service;

import com.ll.basic.domain.Article;
import com.ll.basic.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article write(Article article) {
        Article created = Article
                .builder()
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
        articleRepository.save(created);
        return created;
    }

}
