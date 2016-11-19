package org.sina.adapter;

/**
 * Created by Jerold on 2016/11/18.
 */
public interface EsAdapter {
    void index();

    boolean createIndex(String index, String settings) throws Exception;

    void createMapping(String index, String type, String mappings) throws Exception;
}
