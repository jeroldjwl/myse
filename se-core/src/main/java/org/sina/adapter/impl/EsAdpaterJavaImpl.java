package org.sina.adapter.impl;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.sina.adapter.EsAdapter;
import org.sina.meta.SearchHit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Jerold on 2016/11/18.
 */
public class EsAdpaterJavaImpl implements EsAdapter {
    private static Client client;

    public EsAdpaterJavaImpl() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    client = getClient();
                }
            }
        }
    }

    private Client getClient() {
        Client client = null;
        try {
            Settings settings = Settings.builder().put("cluster.name", "my-es").build();
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.6"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public boolean createIndex(String index, String settings) throws Exception {
        boolean ret = false;
        if (client == null) {
            throw new Exception("client is null");
        }
        try {
            CreateIndexResponse response = client.admin().indices()
                    .prepareCreate(index).setSettings(settings).get();
            ret = response.isAcknowledged();
        } catch (Exception e) {
            throw e;
        }
        return ret;
    }

    @Override
    public void createMapping(String index, String type, String mappings) throws Exception {
        try {
            client.admin().indices().preparePutMapping(index).setType(type)
                    .setSource(mappings)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void index(String index, String type, List<String> users) {
        BulkRequestBuilder brb = client.prepareBulk();
        for (String user : users) {
            IndexRequest ir = client.prepareIndex(index, type).setSource(user).request();
            brb.add(ir);
        }
        BulkResponse bulkResponse = brb.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }

    @Override
    public SearchHit doSearch() {
        return null;
    }
}
