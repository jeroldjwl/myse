package org.shyp.query.impl;

import org.junit.Test;
import org.shyp.query.QueryMetaData;
import org.shyp.search.Searcher;

/**
 * Created by Jerold on 16/6/18.
 */
public class SearcherImplTest {
    @Test
    public void search() throws Exception {
        Searcher searcher = new SearcherImpl();
        searcher.search(new QueryMetaData());
    }

}