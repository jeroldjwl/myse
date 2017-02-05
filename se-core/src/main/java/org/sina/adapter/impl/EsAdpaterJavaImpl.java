package org.sina.adapter.impl;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.sina.adapter.EsAdapter;
import org.sina.meta.EsClause;
import org.sina.meta.EsFilter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Jerold on 2016/11/18.
 */
public class EsAdpaterJavaImpl implements EsAdapter {
    private static Client client;
    private static final String DEFAULT_ALL_FIELD = "all_chinese";

    public EsAdpaterJavaImpl() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    client = getClient();
                }
            }
        }
    }

    private Client getClient() {
        Client client = null;
        try {
            Settings settings = Settings.builder().put("cluster.name", "my-es").build();
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.5"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public boolean createIndex(String index, String settings) throws Exception {
        boolean ret = false;
        if (client == null) {
            throw new Exception("client is null");
        }
        try {
            CreateIndexResponse response = client.admin().indices()
                    .prepareCreate(index).setSettings(settings).get();
            ret = response.isAcknowledged();
        } catch (Exception e) {
            throw e;
        }
        return ret;
    }

    @Override
    public void createMapping(String index, String type, String mappings) throws Exception {
        try {
            client.admin().indices().preparePutMapping(index).setType(type)
                    .setSource(mappings)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void index(String index, String type, List<String> users) {
        BulkRequestBuilder brb = client.prepareBulk();
        for (String user : users) {
            IndexRequest ir = client.prepareIndex(index, type).setSource(user).request();
            brb.add(ir);
        }
        BulkResponse bulkResponse = brb.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }

    @Override
    public void doBasicSearch(String[] indices, String[] types, String keyword, int from, int size, EsClause[] clauses, EsFilter[] filters) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(keyword).defaultField(DEFAULT_ALL_FIELD);
        boolQueryBuilder.must(queryBuilder);
        for (EsClause clause : clauses) {
            String cName = clause.getName();
            String cValue = clause.getValue();
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(cName, cValue);
            matchQueryBuilder.type(null);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        boolQueryBuilder.minimumNumberShouldMatch(1);
        SearchResponse sr = client.prepareSearch(indices).setTypes(types)
                .setFrom(from).setSize(size).setQuery(boolQueryBuilder)
                .execute().actionGet();
        System.out.println("===============================");
        System.out.println(boolQueryBuilder.toString());
        System.out.println("===============================");
        for (org.elasticsearch.search.SearchHit hit : sr.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }
}
