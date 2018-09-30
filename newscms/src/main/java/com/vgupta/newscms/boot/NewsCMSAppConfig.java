package com.vgupta.newscms.boot;

import com.vgupta.newscms.crawler.ArticlesCrawler;
import com.vgupta.newscms.crawler.ArticlesCrawlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
@ComponentScan({"com.vgupta.newscms"})
public class NewsCMSAppConfig {
    @Bean
    public ArticlesCrawlerFactory newFactory(ArticlesCrawler articlesCrawler){
        return new ArticlesCrawlerFactory(articlesCrawler);
    }
}
