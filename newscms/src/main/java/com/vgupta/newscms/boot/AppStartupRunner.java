package com.vgupta.newscms.boot;


import com.vgupta.newscms.crawler.ArticlesCrawlerFactory;
import com.vgupta.newscms.service.NewsCMSService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppStartupRunner implements ApplicationRunner {

    public static final String NEWS_WEBSITE_URL = "http://www.bbc.com/";

    @Autowired
    private ArticlesCrawlerFactory articlesCrawlerFactory;

    @Autowired
    private NewsCMSService newsCMSService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startCrawler();
    }

    @Scheduled(cron = "0 13 * * * ?")
    public void startCrawler() throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        //setting maximum pages to 2000
        config.setMaxPagesToFetch(2000);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed(NEWS_WEBSITE_URL);

        /*
        * removing older articles before starting fresh crawl
        * It will make sure you have only the latest articles
         */
        newsCMSService.deleteExistingArticles();
        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */

        controller.start(articlesCrawlerFactory, numberOfCrawlers);
    }
}
