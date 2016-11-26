package org.sina.admin.impl;

import org.junit.Test;
import org.sina.admin.Admin;

/**
 * Created by Jerold on 2016/11/20.
 */
public class EsAdminJavaImplTest {
    Admin admin;

    public EsAdminJavaImplTest() {
        admin = new EsAdminJavaImpl();
    }

    @Test
    public void deploySource() throws Exception {
        admin.deploySource("sina", "users", 1);
    }

    @Test
    public void undeploySource() throws Exception {

    }

    @Test
    public void crawlSource() throws Exception {
        admin.crawlSource("", "");
    }

}