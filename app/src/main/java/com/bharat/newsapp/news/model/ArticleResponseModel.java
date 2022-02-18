package com.bharat.newsapp.news.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ArticleResponseModel extends BaseModel {
    @SerializedName("totalResults")
    int totalResults;
    @SerializedName("articles")
    public ArrayList<Article> articlesList;

    public class Article implements Serializable {
        @SerializedName("source")
        public Source source;
        @SerializedName("author")
        public String author;
        @SerializedName("title")
        public String title;
        @SerializedName("description")
        public String description;
        @SerializedName("url")
        public String url;
        @SerializedName("urlToImage")
        public String urlToImage;
        @SerializedName("publishedAt")
        public String publishedAt;
        @SerializedName("content")
        public String content;
    }

    public class Source implements Serializable {
        @SerializedName("id")
        String id;
        @SerializedName("name")
        public String name;
    }

}
