package com.example.article.controller;

import com.example.article.entity.Article;
import com.example.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article createdArticle = articleService.createArticle(article);
        return ResponseEntity.ok(createdArticle);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticles(
            @RequestParam int cursor,
            @RequestParam int pageSize) {
        List<Article> articles = articleService.getArticles(cursor, pageSize);
        return ResponseEntity.ok(articles);
    }
}
