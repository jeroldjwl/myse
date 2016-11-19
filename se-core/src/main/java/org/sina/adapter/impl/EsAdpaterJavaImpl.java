package org.sina.adapter.impl;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.sina.adapter.EsAdapter;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.168.1.3"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("101.168.1.5"), 9301));
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
    public void index() {
        client.prepareBulk().execute().actionGet();
    }
}
