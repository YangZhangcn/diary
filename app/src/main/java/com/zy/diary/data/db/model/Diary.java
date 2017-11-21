package com.zy.diary.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangyang on 2017/10/31.
 */
@Entity
public class Diary {
    @Id
    private long id;

    private String date;

    private String weather;

    private String content;

    private String tags;

    private String title;

    @Generated(hash = 1914632684)
    public Diary(long id, String date, String weather, String content, String tags,
            String title) {
        this.id = id;
        this.date = date;
        this.weather = weather;
        this.content = content;
        this.tags = tags;
        this.title = title;
    }

    @Generated(hash = 112123061)
    public Diary() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
