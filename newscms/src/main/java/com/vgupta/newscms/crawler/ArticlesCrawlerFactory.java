package com.vgupta.newscms.crawler;

import com.vgupta.newscms.model.Article;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticlesCrawlerFactory  implements CrawlController.WebCrawlerFactory<ArticlesCrawler>{

    private ArticlesCrawler articlesCrawler;

    @Autowired
    public ArticlesCrawlerFactory(ArticlesCrawler articlesCrawler ) {
        this.articlesCrawler = articlesCrawler;
    }

    @Override
    public ArticlesCrawler newInstance() throws Exception {
        return articlesCrawler;
    }
}
