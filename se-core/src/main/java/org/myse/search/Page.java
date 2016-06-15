package org.myse.search;

import java.util.Date;

/**
 * Created by Jerold on 16/5/29.
 */
public abstract class Page {
    private String url;
    private String title;
    private String type;
    private String content;
    private Date createTime;
    private Date lastCrawledTime;

    public Page() {

    }

    public Page(String url, String title, String type, String content) {
        this.url = url;
        this.title = title;
        this.type = type;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
