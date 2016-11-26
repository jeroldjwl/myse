package org.sina;

import org.sina.meta.QueryMetaData;
import org.sina.meta.SearchHit;

/**
 * Created by Jerold on 2016/11/15.
 */
public interface SearchService {
    public SearchHit search(QueryMetaData qmd);
}
