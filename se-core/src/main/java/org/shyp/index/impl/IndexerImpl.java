package org.shyp.index.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.shyp.search.Indexer;
import org.shyp.search.SearchFactory;
import org.shyp.search.impl.AttachmentPage;
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

    public void index(AttachmentPage page) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", page.getUrl().hashCode());
        document.addField("url", page.getParentPage());
        document.addField("title", page.getTitle());
        document.addField("content", page.getContent());
        document.addField("type", page.getType());
        try {
            UpdateResponse response = solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("index successfully");
    }

    @Override
    public void createSettings(String index) {
        //TODO
    }

    @Override
    public void createMapping(String index, String type) {
        //TODO
    }
}
