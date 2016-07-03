package org.shyp.search;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by Jerold on 16/5/26.
 */
public interface Searcher {
    void search() throws IOException, SolrServerException;
}
