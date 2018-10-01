package com.vgupta.newscms.repository;

import com.vgupta.newscms.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ArticleRepositoryImpl implements ArticleRepository {
    public static final int RESULT_LIMIT = 100;
    public static final String QUERY_DELIMITER = "+";
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ArticleRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public void saveArticle(Article article){
        mongoTemplate.save(article);
    }

    /*Keywords is '+' seperated keywords
    * For eq if you want to search article with either india or newspaper then keywords will be "india+newspaper"
    */
    @Override
    public List<Article> getArticleByKeywords(String keywords) {
        String[] keywordsList = keywords.split(Pattern.quote(QUERY_DELIMITER));
        TextCriteria criteria = TextCriteria.forDefaultLanguage()
                .matchingAny(keywordsList);

        Query query = TextQuery.queryText(criteria)
                .sortByScore().limit(RESULT_LIMIT);

        List<Article> articles = mongoTemplate.find(query, Article.class);
        return articles;
    }

    @Override
    public void deleteExistingArticles() {
        /*Although Drop collection is a performant  way to delete all the article
        * But since drop will remove the indexed also so using remove to avoid that
        */
        mongoTemplate.remove(new Query(), "article");
    }
}
