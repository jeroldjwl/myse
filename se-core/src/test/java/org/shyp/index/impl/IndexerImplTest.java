package org.shyp.index.impl;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.shyp.search.impl.AttachmentPage;
import org.shyp.util.AttachmentUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Jerold on 16/6/4.
 */
public class IndexerImplTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: index(Page page)
     */
    @Test
    public void testIndex() throws Exception {
        PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));
        AttachmentPage page = new AttachmentPage();
        page.setTitle("test attachment");
        page.setType("pdf");
        InputStream is = new FileInputStream(new File("/Users/jerold/Downloads/Data Structures and Algorithms in Java, 6th Edition-Wiley(2014).pdf"));
        String content = AttachmentUtil.extractContent(is);
        page.setContent(content);
        page.setParentPage("http://localhost:8080/testpage");
        page.setUrl("http://localhost:8080/testpage");
        IndexerImpl indexer = new IndexerImpl();
        indexer.index(page);
        System.out.println(content);
    }


}