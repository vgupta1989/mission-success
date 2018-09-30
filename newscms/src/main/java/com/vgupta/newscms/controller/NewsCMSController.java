package com.vgupta.newscms.controller;

import com.vgupta.newscms.model.Article;
import com.vgupta.newscms.service.NewsCMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/newscms")
public class NewsCMSController {

    @Autowired
    private NewsCMSService newsCMSService;

    @GetMapping(value = "/search")
    public List<Article> getData(@RequestParam("query") String query) {
       return newsCMSService.getNewArticlesByKeywords(query);
    }
}
