package com.vgupta.newscms.service;

import com.vgupta.newscms.model.Article;
import com.vgupta.newscms.repository.ArticleRepository;
import com.vgupta.newscms.repository.ArticleRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class NewsCMSServiceImpl implements NewsCMSService {

    @Autowired
    ArticleRepository articlesRepository;

    public void saveArticle(Article article){
        articlesRepository.saveArticle(article);
    }

    public List<Article> getNewArticlesByKeywords(String keyword){
        return articlesRepository.getArticleByKeywords(keyword);
    }
}
