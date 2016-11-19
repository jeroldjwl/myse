package org.sina.util;

/**
 * Created by Jerold on 2016/11/19.
 */
public class EsUtil {
    public static final String META_DATA_ANALYSIS = "analysis";
    public static final String META_DATA_TOKENIZER = "tokenizer";
    public static final String META_DATA_ANALYZER = "analyzer";
    public static final String META_DATA_CHAR_FILTER = "char_filter";
    public static final String META_DATA_FILTER = "filter";
    public static final String META_DATA_STOP_WORDS = "stopwords";
    public static final String META_DATA_ALL_PREFIX = "_all";
    public static final String META_DATA_FACET_PREFIX = "raw";
    public static final String META_DATA_PROPERTIES = "properties";
    public static final String META_DATA_ENABLE = "enable";
    public static final String NUMBER_OF_SHARDS = "number_of_shards";
    public static final String NUMBER_OF_REPLICAS = "number_of_replicas";

    enum Language {
        English, Chinese, Japanese
    }

}
