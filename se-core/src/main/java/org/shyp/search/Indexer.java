package org.shyp.search;

import org.shyp.search.impl.AttachmentPage;

/**
 * Created by Jerold on 16/5/26.
 */
public interface Indexer {
    void index(AttachmentPage page);

    void createSettings(String index);

    void createMapping(String index, String type);
}
