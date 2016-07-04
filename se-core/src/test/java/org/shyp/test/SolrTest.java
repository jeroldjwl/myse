package org.shyp.test;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerold on 16/6/5.
 */
public class SolrTest {
    public static void main(String[] args) {
        try {
            test1();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    public static void test1() throws IOException, SolrServerException {
        String url = "http://localhost:8983/solr/mydata";
        SolrClient client = new HttpSolrClient(url);
        SolrRequest schemaRequest = new SchemaRequest();
        NamedList list = client.request(schemaRequest);
        SimpleOrderedMap map = (SimpleOrderedMap) list.get("schema");
        System.out.println(map.get("fields"));
        ArrayList list1 = (ArrayList) map.get("fields");
        System.out.println("=========================");
        SimpleOrderedMap som = null;
        for (Object o : list1) {
            SimpleOrderedMap m = (SimpleOrderedMap) o;
            if (null != m && "content".equals(m.get("name"))) {
                som = m;
            }
        }
        System.out.println(som);
        int idx = som.indexOf("type", 0);
        som.setVal(idx, "text_gernal");
        System.out.println("=========================");
        System.out.println(som);

        Map map1 = new HashMap();
        map.add("name", "content");
        map.add("type", "text_gernal");

        schemaRequest = new SchemaRequest.ReplaceField(map1);
        client.request(schemaRequest);
        schemaRequest = new SchemaRequest();
        NamedList list2 = client.request(schemaRequest);
        SimpleOrderedMap map2 = (SimpleOrderedMap) list2.get("schema");
        ArrayList list3 = (ArrayList) map2.get("fields");
        for (Object o : list3) {
            SimpleOrderedMap m = (SimpleOrderedMap) o;
            if (null != m && "content".equals(m.get("name"))) {
                som = m;
            }
        }
        System.out.println("=========================");
        System.out.println(som);
    }
}
