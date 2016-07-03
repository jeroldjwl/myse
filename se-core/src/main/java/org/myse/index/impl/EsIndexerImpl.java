package org.myse.index.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.myse.search.SearchFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jerold on 2016/7/3.
 */
public class EsIndexerImpl {
    private Client client;

    public EsIndexerImpl() {
        init();
    }

    private void init() {
        client = SearchFactory.getEsClient();
    }

    public void index() {
        ObjectMapper mapper = new ObjectMapper();

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
