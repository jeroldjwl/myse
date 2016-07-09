package org.shyp.service;

import org.shyp.dao.SearchUrlDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Jerold on 16/7/3.
 */
public class SearchUrlService {
    //if we use SpringFramework, we can add AutoWired annotation on it to load SearchUrlDao automatically
    SearchUrlDao searchUrlDao;

    public SearchUrlService() {
        searchUrlDao = new SearchUrlDao();
    }

    public int addUrl(String url, Date createTime, Date updateTime) {
        return searchUrlDao.addUrl(url, createTime, updateTime);
    }

    public int updateUrl(String url, Date updateTime) {
        return searchUrlDao.updateUrl(url, updateTime);
    }

    public List getAllUrls() {
        return searchUrlDao.getAllUrls();
    }
}
