package org.shyp.search;

import org.shyp.query.QueryMetaData;

/**
 * Created by Jerold on 16/5/26.
 */
public interface Searcher {
    void search(QueryMetaData qmd);
}
