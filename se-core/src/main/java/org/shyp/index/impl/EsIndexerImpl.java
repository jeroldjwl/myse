package org.shyp.index.impl;

import org.apache.solr.client.solrj.SolrServerException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.shyp.search.Indexer;
import org.shyp.search.SearchFactory;
import org.shyp.search.impl.AttachmentPage;
import org.shyp.service.SearchUrlService;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jerold on 2016/7/3.
 */
public class EsIndexerImpl implements Indexer {
    private Client client;
    private String index;
    private String type;
    private SearchUrlService service;

    public EsIndexerImpl() {
        init();
    }

    private void init() {
        client = SearchFactory.getEsClient();
        index = "shyp";
        type = "xxmh";
        service = new SearchUrlService();
    }

    @Override
    public void index(AttachmentPage page) throws IOException, SolrServerException {
        Map doc = new HashMap();
        doc.put("_id", page.getUrl());
        doc.put("url", page.getUrl());
        doc.put("title", page.getTitle());
        doc.put("parentPage", page.getParentPage());
        doc.put("content", page.getContent());
        doc.put("createTime", page.getCreateTime());
        IndexResponse response = client.prepareIndex(index, type).setSource(doc).get();
        String id = response.getId();
        service.addUrl(id, new Date(), new Date());
    }

}
