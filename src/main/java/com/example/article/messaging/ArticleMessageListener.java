package com.example.article.messaging;

import com.example.article.entity.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleMessageListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String ARTICLE_CACHE_KEY = "ARTICLE_CACHE";

    @Autowired
    private ObjectMapper objectMapper;

    public void receiveMessage(String message) {
        try {
            Article article = objectMapper.readValue(message, Article.class);
            redisTemplate.opsForHash().put(ARTICLE_CACHE_KEY, article.getId().toString(), article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
