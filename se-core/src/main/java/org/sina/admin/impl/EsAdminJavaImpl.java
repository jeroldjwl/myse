package org.sina.admin.impl;

import org.sina.EsFactory;
import org.sina.adapter.EsAdapter;
import org.sina.admin.Admin;
import org.sina.data.ExcelDataExtractor;
import org.sina.data.Extractor;
import org.sina.service.MetaDataService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jerold on 2016/11/19.
 */
public class EsAdminJavaImpl implements Admin {
    MetaDataService mds;

    public EsAdminJavaImpl() {
        mds = new MetaDataService();
    }

    @Override
    public void deploySource(String index, String type, int operation) {
        EsAdapter adapter = EsFactory.getEsAdapter(operation);
        try {
            adapter.createIndex(index, mds.generateSettings());
            adapter.createMapping(index, type, mds.generateMappings(type));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undeploySource(String index, String type, int operation) {

    }

    @Override
    public void crawlSource(String source, String type) {
        try {
            InputStream is = new FileInputStream(new File("/Users/jerold/Downloads/普通用户信息.xlsx"));
            Extractor extractor = new ExcelDataExtractor();
            extractor.extract(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
