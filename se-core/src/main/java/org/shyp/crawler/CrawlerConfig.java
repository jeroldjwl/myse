package org.shyp.crawler;

/**
 * Created by Jerold on 16/5/27.
 */
public class CrawlerConfig {
    public CrawlerConfig() {

    }

    public static final String CRAWL_LIMIT_PATH = "http://www.shyp.gov.cn/";

    public static final String CRWL_PATH = "http://www.shyp.gov.cn/";

    public static final int THREAD_NUM = 5;

    protected int mThreads;

    private String[] seeds;

    public int getThreads() {
        return mThreads;
    }

    public void setThreads(int mThreads) {
        this.mThreads = mThreads;
    }

    public String[] getSeeds() {
        return seeds;
    }

    public void setSeeds(String[] seeds) {
        this.seeds = seeds;
    }
}
