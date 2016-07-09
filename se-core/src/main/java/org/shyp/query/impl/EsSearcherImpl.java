package org.shyp.query.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsLookupQueryBuilder;
import org.elasticsearch.search.SearchHit;
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
        index = "shyp";
        type = "xxmh";
    }

    @Override
    public void search(QueryMetaData qmd) {
        String query = qmd.getQuery();
        int pageSize = qmd.getPageNum();
        int offset = qmd.getOffset();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("all-zh", query));
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(queryBuilder)
                .setFrom(offset)
                .setSize(pageSize)
                .execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        TermsLookupQueryBuilder tqb = QueryBuilders
                .termsLookupQuery("sec_attr1")
                .lookupIndex("ecsf_crm_security")
                .lookupType("crm_runtime.EmpView")
                .lookupId("jerold")
                .lookupPath("acl.sec_attr1");
        queryBuilder.must(tqb);
        System.out.println();
        SearchHit hit = hits[0];
        System.out.println(hit.getSourceAsString());
    }

    public static void main(String[] args) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("all-zh", "uu"));
        TermsLookupQueryBuilder tqb = QueryBuilders
                .termsLookupQuery("sec_attr1")
                .lookupIndex("ecsf_crm_security")
                .lookupType("crm_runtime.EmpView")
                .lookupId("jerold")
                .lookupPath("acl.sec_attr1");
        queryBuilder.must(tqb);
        System.out.println(queryBuilder.toString());

    }
}
