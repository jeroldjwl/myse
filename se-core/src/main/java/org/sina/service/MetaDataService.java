package org.sina.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.sina.util.EsUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jerold on 2016/11/19.
 */
public class MetaDataService {
    private Set<String> analyzers;
    private static final String DEFAULT_LANGUAGE_FIELD = "all-chinese";

    //create a MetaDataService depends on the configuration.
    //for now just use the default.
    public MetaDataService() {
        analyzers = new HashSet<>();
        analyzers.add("chinese");
    }

    public String generateMappings(String type) {
        // The searchable attributes should be defined in somewhere like xml file.
        /*JSONObject mapping = new JSONObject();
        JSONObject enable = new JSONObject();
        enable.put(EsUtil.META_DATA_ENABLE, false);
        mapping.put(EsUtil.META_DATA_ALL_PREFIX, enable);
        JSONObject properties = new JSONObject();
        //properties.put(generateAllMappingKeys("english"), generateAllMappingValues("english"));
        //properties.put(generateAllMappingKeys("chinese"), generateAllMappingValues("chinese"));*/
        //mapping.put(EsUtil.META_DATA_PROPERTIES, generateProperties());
        return generateProperties();
    }

    private String generateProperties() {
        String property = "{\n" +
                "\"_all\":{\n" +
                "\"enabled\":false\n" +
                "},\n" +
                "\"properties\":{\n" +
                "\"user_id\":{\n" +
                "\"type\":\"long\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"nick_name\":{\n" +
                "\"type\":\"string\",\n" +
                "\"analyzer\":\"custom-chinese\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"location\":{\n" +
                "\"type\":\"nested\",\n" +
                "\"properties\":{\n" +
                "\"province\":{\n" +
                "\"type\":\"string\",\n" +
                "\"index\":\"not_analyzed\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"city\":{\n" +
                "\"type\":\"string\",\n" +
                "\"index\":\"not_analyzed\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "}\n" +
                "}\n" +
                "}\n," +
                "\"description\":{\n" +
                "\"type\":\"string\",\n" +
                "\"analyzer\":\"custom-chinese\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"picSrc\":{\n" +
                "\"type\":\"string\",\n" +
                "\"index\":\"no\"\n" +
                "},\n" +
                "\"gender\":{\n" +
                "\"type\":\"string\",\n" +
                "\"index\":\"not_analyzed\"\n" +
                "},\n" +
                "\"fans\":{\n" +
                "\"type\":\"integer\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"follow\":{\n" +
                "\"type\":\"integer\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"weiboNum\":{\n" +
                "\"type\":\"integer\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"collect\":{\n" +
                "\"type\":\"integer\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"createDate\":{\n" +
                "\"type\":\"date\",\n" +
                "\"index\":\"not_analyzed\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"certification\":{\n" +
                "\"type\":\"string\",\n" +
                "\"analyzer\":\"custom-chinese\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"friends\":{\n" +
                "\"type\":\"integer\",\n" +
                "\"copy_to\":[\"all_chinese\"]\n" +
                "},\n" +
                "\"all_chinese\":{\n" +
                "\"type\":\"string\",\n" +
                "\"analyzer\":\"custom-chinese\"\n" +
                "}\n" +
                "}\n" +
                "}\n";
        return property;
    }

    private String createFieldMapping(String fieldName, String type, boolean isFacetField) {
        JSONObject field = new JSONObject();
        field.put(fieldName, "");
        JSONObject fieldMappingVal = new JSONObject();
        fieldMappingVal.put("type", type);
        if ("date".equals(type)) {
        } else if ("long".equals(type) || "int".equals(type)
                || "short".equals(type) || "double".equals(type)) {

        } else {
            if (isFacetField) {

            }
        }
        field.put("copy_to", DEFAULT_LANGUAGE_FIELD);
        return field.toJSONString();
    }

    private String generateAllMappingKeys(String language) {
        return "all_" + language;
    }

    private String generateAllMappingValues(String language) {
        JSONObject allValues = new JSONObject();
        allValues.put("type", "string");
        allValues.put("analyzer", "custom-" + language);
        return allValues.toJSONString();
    }

    public String generateSettings() {
        JSONObject settings = new JSONObject();
        JSONObject analysis = new JSONObject();
        settings.put(EsUtil.NUMBER_OF_SHARDS, 3);
        settings.put(EsUtil.NUMBER_OF_REPLICAS, 2);
        analysis.put(EsUtil.META_DATA_FILTER, generateFilter());
        analysis.put(EsUtil.META_DATA_TOKENIZER, generateTokenizer());
        analysis.put(EsUtil.META_DATA_CHAR_FILTER, generateCharFilter());
        analysis.put(EsUtil.META_DATA_ANALYZER, generateAnalyzer());
        settings.put(EsUtil.META_DATA_ANALYSIS, analysis);
        return settings.toJSONString();
    }

    private JSONObject generateAnalyzer() {
        JSONObject analyzer = new JSONObject();
        for (String ana : analyzers) {
            if ("chinese".equals(ana)) {
                JSONObject cnAnalyzer = new JSONObject();
                cnAnalyzer.put("type", "custom");
                cnAnalyzer.put("tokenizer", "cn_tokenizer");
                JSONArray jsonArray = new JSONArray();
                jsonArray.add("cjk_width");
                jsonArray.add("lowercase");
                jsonArray.add("cjk_bigram");
                jsonArray.add("cn_stopwords");
                cnAnalyzer.put("filter", jsonArray);
                analyzer.put("custom-chinese", cnAnalyzer);
            } else if ("english".equals(ana)) {
                JSONObject enAnalyzer = new JSONObject();
                enAnalyzer.put("type", "standard");
                enAnalyzer.put("tokenizer", "standard");
                analyzer.put("custom-english", enAnalyzer);
            }
        }
        return analyzer;
    }

    private JSONObject generateCharFilter() {
        return null;
    }

    private JSONObject generateTokenizer() {
        JSONObject tokenizer = new JSONObject();
        for (String ana : analyzers) {
            if ("chinese".equals(ana)) {
                JSONObject cnTokenizer = new JSONObject();
                cnTokenizer.put("type", "standard");
                cnTokenizer.put("max_token_length", 900);
                tokenizer.put("cn_tokenizer", cnTokenizer);
            }
        }
        return tokenizer;
    }

    private JSONObject generateFilter() {
        JSONObject filter = new JSONObject();
        for (String ana : analyzers) {
            if ("chinese".equals(ana)) {
                JSONObject cnFilter = new JSONObject();
                cnFilter.put("type", "stop");
                cnFilter.put("stopwords", "_chinese_");
                filter.put("cn_stopwords", cnFilter);
            }
        }
        return filter;
    }

}
