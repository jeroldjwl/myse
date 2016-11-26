package org.sina.service.impl;

import org.sina.SearchService;
import org.sina.meta.QueryMetaData;
import org.sina.meta.SearchHit;

/**
 * Created by Jerold on 2016/11/23.
 */
public class SearchServiceImpl implements SearchService {

    public SearchServiceImpl() {
    }

    @Override
    public SearchHit search(QueryMetaData qmd) {
        String keyword = qmd.getKeyword();

        return null;
    }
}
