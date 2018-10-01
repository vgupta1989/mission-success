package com.vgupta.newscms.crawler;

import com.vgupta.newscms.model.Article;
import edu.uci.ics.crawler4j.crawler.Page;

public interface ArticleExtractor {
   public Article extractArticle(Page webPage);
}
