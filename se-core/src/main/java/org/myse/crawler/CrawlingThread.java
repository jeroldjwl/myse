package org.myse.crawler;

import org.myse.crawler.impl.CrawlerImpl;

/**
 * Created by Jerold on 16/6/2.
 */
public class CrawlingThread implements Runnable {

    CrawlerImpl crawler;

    private int threadId;

    public CrawlingThread(int id, CrawlerImpl crawler) {
        this.threadId = id;
        this.crawler = crawler;
    }

    @Override
    public void run() {
        try {
            crawler.fullCrawl(threadId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
