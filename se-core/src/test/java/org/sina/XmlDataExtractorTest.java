package org.sina;

import org.junit.Before;
import org.junit.Test;
import org.sina.data.XmlDataExtractor;

import java.io.*;

/**
 * Created by Jerold on 2016/11/15.
 */
public class XmlDataExtractorTest {
    private XmlDataExtractor xde;

    @Before
    public void before() {
        xde = new XmlDataExtractor();
    }

    @Test
    public void extractTest() {
        try {
            InputStream is = new FileInputStream(new File("/Users/jerold/Downloads/普通用户信息.xlsx"));
            xde.extract(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeTest() {

    }
}
