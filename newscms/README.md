# News Content Manager
News Content Manager(NewsCMS) is a system that crawls for articles from a news website, cleanses the response, stores in a mongo database then makes it available to search via an API.

## Details

### Technology Involved

- [crawler4j](https://github.com/yasserg/crawler4j) For Web Crawling
- [jsoup](https://github.com/jhy/jsoup/) for HTML Parsing
- JAVA 8
- Gradle
- MongoDB
- SpringBoot

This Service is running at aws at 54.175.160.15. So if you want to test search you can use this instance. For eg if you want to get article containing Trump, you can query:- 54.175.160.15/newscms/search?query=trump. It will return top 100 articles having Trump.
