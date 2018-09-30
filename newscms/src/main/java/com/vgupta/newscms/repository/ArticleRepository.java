package com.vgupta.newscms.repository;

import com.vgupta.newscms.model.Article;

import java.util.List;

public interface ArticleRepository {
    public void saveArticle(Article article);

    public List<Article> getArticleByKeywords(String keyword);

}

