package org.shyp.query;

/**
 * Created by Jerold on 16/5/26.
 */
public class QueryMetaData {
    private String query;
    private int pageNum;
    private int offset;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
