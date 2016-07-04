package org.shyp.search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.shyp.crawler.impl.CrawlerImpl;
import org.shyp.index.impl.EsIndexerImpl;
import org.shyp.query.impl.SearcherImpl;
import org.shyp.util.SearchUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Jerold on 16/5/29.
 */
public class SearchFactory {
    private static SolrClient solrClient;

    private static Client client;

    static {
        String url = "http://localhost:8983/solr/mydata";
        solrClient = new HttpSolrClient(url);
        try {
            client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static SolrClient getClient() {
        return solrClient;
    }

    public static Client getEsClient() {
        return client;
    }

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
        return new EsIndexerImpl();
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
