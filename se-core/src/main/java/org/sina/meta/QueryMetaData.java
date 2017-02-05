package org.sina.meta;

/**
 * Created by Jerold on 2016/11/23.
 */
public class QueryMetaData {
    private String keyword;
    private int pageSize;
    private int from;
    private int to;
    private String[] indices;
    private String[] types;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void addIndices(String[] index) {
        this.indices = index;
    }

    public String[] getIndices() {
        return this.indices;
    }

    public void addTypes(String[] types) {
        this.types = types;
    }

    public String[] getTypes() {
        return this.types;
    }
}
