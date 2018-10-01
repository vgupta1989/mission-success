package com.vgupta.newscms.crawler;

import com.vgupta.newscms.model.Article;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class BBCArticleExtractorTest {

    private BBCArticleExtractor bbcArticleExtractor;

    private Page page;

    @Before
    public void setUp() throws Exception {
        bbcArticleExtractor = new BBCArticleExtractor();
        WebURL webURL = new WebURL();
        webURL.setURL("https://www.bbc.com/news/indonesia-45702609");
        page = new Page(webURL);


    }

    @Test
    public void extractArticle_withNewsStory() throws IOException, URISyntaxException {
        //given
        HtmlParseData htmlParseData = new HtmlParseData();
        String html = getStringFromResourceFile("articlewithstory.txt");
        htmlParseData.setHtml(html);
        page.setParseData(htmlParseData);
        //when
        Article article = bbcArticleExtractor.extractArticle(page);
        //then
        assertNotNull(article);
        assertNotNull(article.getContent());
        assertNotNull(article.getTitle());
    }

    @Test
    public void extractArticle_withOutStory() throws URISyntaxException, IOException {
        //given
        HtmlParseData htmlParseData = new HtmlParseData();
        String html = getStringFromResourceFile("articlewithoutstory.txt");
        htmlParseData.setHtml(html);
        page.setParseData(htmlParseData);
        //when
        Article article = bbcArticleExtractor.extractArticle(page);

        //then
        assertNull(article);
    }

    private String getStringFromResourceFile(String resourceFileName) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource(resourceFileName ).toURI());
        Stream<String> lines = Files.lines(path);
        String html = lines.collect(Collectors.joining("\n"));
        lines.close();
        return html;
    }
}