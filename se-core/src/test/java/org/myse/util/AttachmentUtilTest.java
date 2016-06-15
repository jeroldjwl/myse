package org.myse.util;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Jerold on 16/6/4.
 */
public class AttachmentUtilTest {
    @Test
    public void extractContent() throws Exception {
        InputStream is = new FileInputStream(new File("/Users/jerold/Downloads/20160407172903_6972.doc"));
        String content = AttachmentUtil.extractContent(is);
        System.out.println(content);
    }

}