package org.shyp.query.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.shyp.crawler.CrawlerConfig;
import org.shyp.query.QueryMetaData;
import org.shyp.search.SearchFactory;
import org.shyp.search.Searcher;

/**
 * Created by Jerold on 16/7/4.
 */
public class EsSearcherImpl implements Searcher {
    private String index;
    private String type;
    private Client client;

    public EsSearcherImpl() {
        init();
    }

    private void init() {
        client = SearchFactory.getEsClient();
        index = CrawlerConfig.INDEX;
        type = CrawlerConfig.TYPE;
    }

    @Override
    public void search(QueryMetaData qmd) {
        String query = qmd.getQuery();
        int pageSize = qmd.getPageNum();
        int offset = qmd.getOffset();
        QueryStringQueryBuilder queryBuilder = QueryBuilders
                .queryStringQuery(query)
                .defaultField("all-zh");
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(queryBuilder)
                .setFrom(offset)
                .setSize(pageSize)
                .execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        System.out.println("returned result list below:");
        if (hits != null) {
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsString());
            }
        }
        System.out.println("end search");
    }

    public static void main(String[] args) {
        Searcher searcher = SearchFactory.getSearcher();
        QueryMetaData qmd = new QueryMetaData();
        qmd.setQuery("杨浦");
        qmd.setOffset(1);
        qmd.setPageNum(10);
        searcher.search(qmd);
    }
}
