package org.sina.adapter;

import org.sina.meta.SearchHit;

import java.util.List;

/**
 * Created by Jerold on 2016/11/18.
 */
public interface EsAdapter {
    void index(String index, String type, List<String> users);

    boolean createIndex(String index, String settings) throws Exception;

    void createMapping(String index, String type, String mappings) throws Exception;

    SearchHit doSearch();

}
