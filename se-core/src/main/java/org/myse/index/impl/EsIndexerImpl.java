package org.myse.index.impl;

import org.elasticsearch.client.Client;
import org.myse.search.SearchFactory;

/**
 * Created by jerold on 2016/7/3.
 */
public class EsIndexerImpl {
    private Client client;

    public EsIndexerImpl() {
        init();
    }

    private void init() {
        client = SearchFactory.getEsClient();
    }

    public void index() {

    }
}
