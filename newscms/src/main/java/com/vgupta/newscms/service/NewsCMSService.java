package com.vgupta.newscms.service;

import com.vgupta.newscms.model.Article;

import java.util.List;

public interface NewsCMSService {
    public void saveArticle(Article article);
    public List<Article> getNewArticlesByKeywords(String keyword);
}
