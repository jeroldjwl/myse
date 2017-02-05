package org.sina.service.impl;

import org.sina.EsFactory;
import org.sina.SearchService;
import org.sina.adapter.EsAdapter;
import org.sina.meta.EsClause;
import org.sina.meta.QueryMetaData;

/**
 * Created by Jerold on 2016/11/23.
 */
public class SearchServiceImpl implements SearchService {
    private EsAdapter esAdapter;

    public SearchServiceImpl() {
        esAdapter = EsFactory.getEsAdapter(1);
    }

    @Override
    public void search(QueryMetaData qmd) {
        String keyword = qmd.getKeyword();
        String[] indices = qmd.getIndices();
        String[] types = qmd.getTypes();
        int from = qmd.getFrom();
        int size = qmd.getPageSize();
        EsClause clause = new EsClause("nickName", "五星");
        EsClause[] clauses = new EsClause[1];
        clauses[0] = clause;
        esAdapter.doBasicSearch(indices, types, keyword, from, size, clauses, null);
    }
}
