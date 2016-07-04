package org.shyp.crawler.impl;

import org.junit.Test;

/**
 * Created by Jerold on 16/6/4.
 */
public class CrawlerImplTest {
    @Test
    public void retrievePage() throws Exception {
        CrawlerImpl crawler = new CrawlerImpl();
        crawler.retrievePage("http://www.shyp.gov.cn/Attachments/file/20160411/20160411101710_0355.doc");
    }

}