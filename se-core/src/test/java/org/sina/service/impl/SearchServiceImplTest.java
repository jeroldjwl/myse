package org.sina.service.impl;

import org.junit.Test;
import org.sina.SearchService;
import org.sina.meta.QueryMetaData;

/**
 * Created by Jerold on 2016/11/29.
 */
public class SearchServiceImplTest {
    @Test
    public void search() throws Exception {
        SearchService searchService = new SearchServiceImpl();
        QueryMetaData queryMetaData = new QueryMetaData();
        queryMetaData.setKeyword("新疆");
        queryMetaData.setFrom(1);
        queryMetaData.setPageSize(10);
        String[] indices = new String[1];
        indices[0] = "sina";
        String[] types = new String[1];
        types[0] = "user";
        queryMetaData.addIndices(indices);
        queryMetaData.addTypes(types);
        searchService.search(queryMetaData);
    }

}