package com.vgupta.newscms.crawler;

import com.vgupta.newscms.model.Article;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class BBCArticleExtractor implements ArticleExtractor {
    @Override
    public Article extractArticle(Page page) {
        String url = page.getWebURL().getURL();
        Article article = null;

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Document doc = Jsoup.parse(html);
            Elements titleElements = doc.getElementsByClass("story-body__h1");
            Elements content = doc.getElementsByClass("story-body__inner");
            if(titleElements.size() > 0 && content.size() > 0) {
                String heading = titleElements.text();
                Element element = content.get(0);
                Elements elementsWithParaTag = element.getElementsByTag("p");
                Stream<TextNode> textNodeStream = elementsWithParaTag.stream().flatMap(element1 -> element1.textNodes().stream());
                List<String> contentList = textNodeStream.map(TextNode::getWholeText).collect(Collectors.toList());
                String contentText = contentList.stream().collect(Collectors.joining(" "));
                article = new Article(heading, url, contentText);
            }
        }
        return article;
    }
}
