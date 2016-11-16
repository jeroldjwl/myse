package org.sina;

import org.junit.Before;
import org.junit.Test;
import org.sina.data.ExcelDataExtractor;

import java.io.*;
import java.util.Date;

/**
 * Created by Jerold on 2016/11/15.
 */
public class ExcelDataExtractorTest {
    private ExcelDataExtractor xde;

    @Before
    public void before() {
        xde = new ExcelDataExtractor();
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
        String d = "Wed May 11 13:29:05 +0800 2011";
        //String d = "11 16 23:01:30 1016";
        Date date = new Date(d);
        /*SimpleDateFormat sdf = new SimpleDateFormat("MM dd HH:mm:ss yy");
        try {
            date = sdf.parse(d);
            System.out.println(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        System.out.println(date.toString());
    }
}
