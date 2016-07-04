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

    public void createSettings(String index) {
        Map settings = new HashMap();
        Map analysis = new HashMap();
        Map custom_cjk = new HashMap();
        Map<String, Object> inner = new HashMap<String, Object>();
        inner.put("type", "custom");
        inner.put("tokenizer", "standard");
        String[] strs = {"cjk_width", "lowercase", "cjk_bigram", "custom-stopwords-cjk"};
        inner.put("filter", strs);
        custom_cjk.put("custom-cjk", inner);
        Map filter = new HashMap();
        filter.put("type", "stop");
        filter.put("stopwords", "_zh_");
        Map stopwords = new HashMap();
        stopwords.put("custom-stopwords-cjk", filter);
        analysis.put("filter", stopwords);
        analysis.put("analyzer", custom_cjk);
        settings.put("analysis", analysis);

        client.admin().indices().prepareCreate(index).setSettings(settings).get();
        System.out.println("++++++++++++++++++++++++++++++++++++++");
    }


    public void createMapping(String index, String type) {
        client.admin().indices()
                .preparePutMapping(index).setType(type).setSource("{\n" +
                " \"_all\":{\n" +
                "   \"enabled\":false\n" +
                "   },\n" +
                "   \"properties\":{\n" +
                "       \"url\":{\n" +
                "           \"type\":\"string\",\n" +
                "           \"index\":\"not_analyzed\"\n" +
                "       },\n" +
                "       \"title\":{\n" +
                "           \"type\":\"string\",\n" +
                "           \"index\":\"analyzed\",\n" +
                "           \"analyzer\":\"custom-cjk\",\n" +
                "           \"copy_to\":\"all-zh\"\n" +
                "       },\n" +
                "       \"type\":{\n" +
                "           \"type\":\"string\",\n" +
                "           \"index\":\"no\"\n" +
                "        },\n" +
                "       \"content\":{\n" +
                "           \"type\":\"string\",\n" +
                "           \"analyzer\":\"custom-cjk\",\n" +
                "           \"search_analyzer\":\"custom-cjk\",\n" +
                "           \"copy_to\":\"all-zh\"\n" +
                "       },\n" +
                "       \"createTime\":{\n" +
                "           \"type\":\"date\",\n" +
                "           \"index\":\"not_analyzed\"\n" +
                "       },\n" +
                "       \"parentPage\":{\n" +
                "           \"type\":\"string\",\n" +
                "           \"index\":\"not_analyzed\"\n" +
                "        },\n" +
                "       \"all-zh\":{\n" +
                "           \"type\":\"string\",\n" +
                "           \"analyzer\":\"custom-cjk\",\n" +
                "           \"search_analyzer\":\"custom-cjk\"\n" +
                "       }\n" +
                "   }\n" +
                "}")
                .get();
    }

    public static void main(String[] args) {
        EsIndexerImpl indexer = new EsIndexerImpl();
        indexer.createSettings("shyptest");
        indexer.createMapping("shyptest", "xxmh");
    }

}
