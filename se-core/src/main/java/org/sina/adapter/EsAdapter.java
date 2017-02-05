package org.sina.adapter;

import org.sina.meta.EsClause;
import org.sina.meta.EsFilter;

import java.util.List;

/**
 * Created by Jerold on 2016/11/18.
 */
public interface EsAdapter {
    void index(String index, String type, List<String> users);

    boolean createIndex(String index, String settings) throws Exception;

    void createMapping(String index, String type, String mappings) throws Exception;

    void doBasicSearch(String[] indices, String[] types, String keyword, int from, int size, EsClause[] clauses, EsFilter[] filters);

}
