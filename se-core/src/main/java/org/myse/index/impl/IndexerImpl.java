package org.myse.index.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.myse.search.Indexer;
import org.myse.search.SearchFactory;
import org.myse.search.impl.AttachmentPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Jerold on 16/5/27.
 */
public class IndexerImpl implements Indexer {
    Logger logger = LoggerFactory.getLogger(IndexerImpl.class);

    private static SolrClient solrClient;

    public IndexerImpl() {
        init();
    }

    private void init() {
        solrClient = SearchFactory.getClient();
    }

    public void index(AttachmentPage page) throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", page.getUrl().hashCode());
        document.addField("url", page.getParentPage());
        document.addField("title", page.getTitle());
        document.addField("content", page.getContent());
        document.addField("type", page.getType());
        UpdateResponse response = solrClient.add(document);
        solrClient.commit();
        logger.info("index successfully");
    }

}
