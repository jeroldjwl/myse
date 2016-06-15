package org.myse.search;

import org.myse.crawler.impl.CrawlerImpl;
import org.myse.index.impl.IndexerImpl;
import org.myse.query.impl.SearcherImpl;
import org.myse.util.SearchUtil;

/**
 * Created by Jerold on 16/5/29.
 */
public class SearchFactory {
    public static Crawler getCrawler() {
        String className = SearchUtil.getCrawler();
        if (className != null) {
            try {
                Class clazz = Class.forName(className);
                return (Crawler) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new CrawlerImpl();
    }

    public static Indexer getIndexer() {
        String className = SearchUtil.getIndexer();
        if (className != null) {
            try {
                Class clazz = Class.forName(className);
                return (Indexer) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new IndexerImpl();
    }

    public static Searcher getSearcher() {
        String className = SearchUtil.getSearcher();
        if (className != null) {
            try {
                Class clazz = Class.forName(className);
                return (Searcher) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new SearcherImpl();
    }
}
