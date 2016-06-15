package org.myse.crawler;

import org.apache.log4j.PropertyConfigurator;
import org.myse.crawler.impl.CrawlerImpl;

import java.util.ArrayList;

/**
 * Created by Jerold on 16/5/28.
 */
public class MyCrawler {

    public static void main(String[] args) throws InterruptedException {
        PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));
        CrawlerImpl crawler = new CrawlerImpl();
        int threads = CrawlerConfig.THREAD_NUM;
        ArrayList<Thread> threadList = new ArrayList<Thread>(threads);
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(new CrawlingThread(i, crawler));
            thread.start();
            System.out.println("====");
            threadList.add(thread);
            Thread.sleep(10L);
        }
    }
}
