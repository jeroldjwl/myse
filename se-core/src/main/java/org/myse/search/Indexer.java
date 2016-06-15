package org.myse.search;

import org.apache.solr.client.solrj.SolrServerException;
import org.myse.search.impl.AttachmentPage;

import java.io.IOException;

/**
 * Created by Jerold on 16/5/26.
 */
public interface Indexer {
    void index(AttachmentPage page) throws IOException, SolrServerException;
}
