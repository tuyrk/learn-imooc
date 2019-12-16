package com.tuyrk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 小说实体类
 *
 * @author tuyrk
 */
@Data
public class Novel {
    private String title;
    private String author;
    @JsonProperty(value = "word_count")
    @SerializedName("word_count")
    private String wordCount;
    @JsonProperty(value = "publish_date")
    @SerializedName("publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    public Novel(String title, String author, String wordCount, Date publishDate) {
        this.title = title;
        this.author = author;
        this.wordCount = wordCount;
        this.publishDate = publishDate;
    }
}
