package com.vgupta.newscms.repository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.vgupta.newscms.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ArticleRepositoryImpl implements ArticleRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ArticleRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public void saveArticle(Article article){
        mongoTemplate.save(article);
////        boolean result = true;
//        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
//        MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
//        MongoCollection<org.bson.Document> collection = database.getCollection("articles");
////        try {
//            collection.insertOne(new Document("_id", article.getUrl()).append("title", article.getTitle()).append("content", article.getContent()));
////        }
////        catch(MongoException e ){
////            System.out.println(e.getMessage());
////            result = true
////        }

    }

    @Override
    public List<Article> getArticleByKeywords(String keyword) {
        String[] split = keyword.split(Pattern.quote("+"));
        TextCriteria criteria = TextCriteria.forDefaultLanguage()
                .matchingAny(split);

        Query query = TextQuery.queryText(criteria)
                .sortByScore().limit(100);

        List<Article> articles = mongoTemplate.find(query, Article.class);
        return articles;
    }

//    public interface PetsRepository extends MongoRepository<Pets, String>
}
