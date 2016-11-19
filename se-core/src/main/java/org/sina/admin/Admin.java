package org.sina.admin;

/**
 * Created by Jerold on 2016/11/19.
 */
public interface Admin {
    void deploySource(String index, String type, int operation);

    void undeploySource(String index, String type, int operation);

    void crawlSource(String source, String type);

}
