package com.example.article.service;

import com.example.article.entity.Article;
import com.example.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String ARTICLE_CACHE_KEY = "ARTICLE_CACHE";

    @Transactional
    @CachePut(value = ARTICLE_CACHE_KEY, key = "#result.id")
    public Article createArticle(Article article) {
        
        // persist to database
        article.setCreatedAt(LocalDateTime.now());
        Article savedArticle = articleRepository.save(article);

        // write back to cache
        redisTemplate.opsForHash().put(ARTICLE_CACHE_KEY, savedArticle.getId(), savedArticle);

        return savedArticle;
    }

    @Cacheable(value = ARTICLE_CACHE_KEY, key = "#id")
    public Article getArticleById(Long id) {
        return (Article) redisTemplate.opsForHash().get(ARTICLE_CACHE_KEY, id);
    }

    public List<Article> getArticles(int page, int pageSize) {
        Page<Article> articlesPage = articleRepository.findAll(PageRequest.of(page, pageSize));
        return articlesPage.getContent();
    }
}
