package org.sina.service;

import org.json.simple.JSONObject;
import org.sina.util.EsUtil;

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

    }

    public String generateMappings(String type) {
        // The searchable attributes should be defined in somewhere like xml file.
        JSONObject mapping = new JSONObject();
        JSONObject enable = new JSONObject();
        enable.put(EsUtil.META_DATA_ENABLE, false);
        mapping.put(EsUtil.META_DATA_ALL_PREFIX, enable);
        JSONObject properties = new JSONObject();
        properties.put(generateAllMappingKeys("english"), generateAllMappingValues("english"));
        properties.put(generateAllMappingKeys("chinese"), generateAllMappingValues("chinese"));


        mapping.put(EsUtil.META_DATA_PROPERTIES, properties);
        return null;
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
        analysis.put(EsUtil.META_DATA_ANALYZER, generateAnalyzer());
        analysis.put(EsUtil.META_DATA_FILTER, generateFilter());
        analysis.put(EsUtil.META_DATA_TOKENIZER, generateTokenizer());
        analysis.put(EsUtil.META_DATA_CHAR_FILTER, generateCharFilter());
        settings.put(EsUtil.NUMBER_OF_SHARDS, 3);
        settings.put(EsUtil.NUMBER_OF_REPLICAS, 2);
        settings.put(EsUtil.META_DATA_ANALYSIS, analysis);
        return settings.toJSONString();
    }

    private String generateAnalyzer() {
        return null;
    }

    private String generateCharFilter() {
        return null;
    }

    private String generateTokenizer() {
        return null;
    }

    private String generateFilter() {
        return null;
    }
}
