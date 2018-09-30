package com.vgupta.newscms.crawler;

import com.vgupta.newscms.model.Article;
import com.vgupta.newscms.service.NewsCMSService;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class ArticlesCrawler extends WebCrawler {

    @Autowired
    private NewsCMSService newsCMSService;

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.bbc.com/". In Our case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith("https://www.bbc.com/");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Document doc = Jsoup.parse(html);
            Elements body_h1 = doc.getElementsByClass("story-body__h1");
            Elements content = doc.getElementsByClass("story-body__inner");
            if(body_h1.size() > 0 && content.size() > 0) {
                String heading = body_h1.text();
                Element element = content.get(0);
                Elements p = element.getElementsByTag("p");
                Stream<TextNode> textNodeStream = p.stream().flatMap(element1 -> element1.textNodes().stream());
                List<String> contentList = textNodeStream.map(textNode -> textNode.getWholeText()).collect(Collectors.toList());
                String contentText = contentList.stream().collect(Collectors.joining(" "));
                newsCMSService.saveArticle(new Article(heading, url, contentText));
            }
        }
    }
}
