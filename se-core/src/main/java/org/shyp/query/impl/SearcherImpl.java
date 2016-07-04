package org.shyp.query.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.shyp.search.SearchFactory;
import org.shyp.search.Searcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jerold on 16/5/29.
 */
public class SearcherImpl implements Searcher {
    private SolrClient solrClient;

    public SearcherImpl() {
        init();
    }

    private void init() {
        solrClient = SearchFactory.getClient();
    }

    @Override
    public void search() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("江西").setHighlight(true);
        QueryResponse response = solrClient.query(query);
        SolrDocumentList solrDocuments = response.getResults();
        int count = 0;
        Iterator iterator = solrDocuments.iterator();
        while (iterator.hasNext()) {
            SolrDocument sd = (SolrDocument) iterator.next();
            count++;
            ArrayList list = (ArrayList) sd.getFieldValue("content");
            for (Object o : list) {
                System.out.println(o.toString());
                System.out.println("===========================");
            }
        }
        System.out.println(count);
        System.out.println("just a test");
    }
}
