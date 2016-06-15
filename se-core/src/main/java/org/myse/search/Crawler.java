package org.myse.search;

/**
 * Created by Jerold on 16/5/26.
 */
public interface Crawler {
    void fullCrawl(int threadId);

    void incrementalCrawl();
}
