package com.vgupta.newscms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "article")
public class Article {
    @TextIndexed(weight=2)
    private String title;
    @Id
    private String url;
    @TextIndexed
    private String content;
}
