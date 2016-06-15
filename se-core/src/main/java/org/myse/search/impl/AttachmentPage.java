package org.myse.search.impl;

import org.myse.search.Page;

/**
 * Created by Jerold on 16/5/29.
 */
public class AttachmentPage extends Page {
    private String parentPage;

    public AttachmentPage() {

    }

    public AttachmentPage(String url, String title, String type, String content, String parentPage) {
        super(url, title, type, content);
        this.parentPage = parentPage;
    }

    public String getParentPage() {
        return parentPage;
    }

    public void setParentPage(String parentPage) {
        this.parentPage = parentPage;
    }

    @Override
    public String toString() {
        return "AttachmentPage{" +
                "url=" + '\'' + getUrl() +
                " parentPage='" + parentPage + '\'' + " title='" + getTitle() + '\'' + " type='" + getType() + '\'' + " content=" + getContent() + '\'' +
                '}';
    }
}
