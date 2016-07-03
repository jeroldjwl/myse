package org.shyp.crawler.impl;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.shyp.crawler.CrawlerConfig;
import org.shyp.parser.HtmlParserTool;
import org.shyp.search.Crawler;
import org.shyp.search.Indexer;
import org.shyp.search.LinkFilter;
import org.shyp.search.SearchFactory;
import org.shyp.search.impl.AttachmentPage;
import org.shyp.util.AttachmentUtil;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by Jerold on 16/5/27.
 */
public class CrawlerImpl implements Crawler {

    private Set<String> crawledLinks;
    private LinkedList<String> unCrawlLinks;
    private CrawlerConfig config;
    private static String DEFAULT_CHARSET = "GB2312,utf-8;q=0.7,*;q=0.7";
    private Map<String, String> pageRelation;
    private LinkFilter linkFilter;

    private int threads;

    private volatile int count;

    private final Object lock = new Object();

    public CrawlerImpl() {
        init();
    }

    public CrawlerImpl(CrawlerConfig config) {
        this.config = config;
        this.threads = config.getThreads();
        init();
    }

    private void init() {
        if (config == null) {
            config = new CrawlerConfig();
        }
        crawledLinks = new HashSet<String>();
        unCrawlLinks = new LinkedList<String>();
        threads = CrawlerConfig.THREAD_NUM;
        addSeeds();
        pageRelation = new HashMap();
        linkFilter = new LinkFilter() {
            @Override
            public boolean accept(String url) {
                if (url.startsWith(CrawlerConfig.CRAWL_LIMIT_PATH)
                        && !url.contains("+") && !url.contains("[") && !url.contains("(") && !url.contains("void")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    private synchronized void addSeeds() {
        unCrawlLinks.add(CrawlerConfig.CRWL_PATH);
    }

    public void fullCrawl(int threadId) {
        while (true) {
            if (count != threads) {
                try {
                    System.out.println("线程:" + threadId + " 正在crawl");
                    crawl();
                } catch (ConnectTimeoutException e) {
                    continue;
                } catch (SocketTimeoutException e) {
                    continue;
                } catch (UnknownHostException e) {
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else break;
        }
    }

    public void incrementalCrawl() {

    }

    public void crawl() throws Exception {
        String url = getNext();
        if (url != null) {
            //解析网页提取URL
            addToCrawled(url);
            retrievePage(url);
        } else {
            synchronized (lock) {
                count++;
                if (count != threads) {
                    lock.wait();
                    System.out.println("当前有" + count + "个线程在等待");
                } else {
                    lock.notifyAll();
                }
            }
        }
    }

    public void retrievePage(String url) throws IOException {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept-Charset", DEFAULT_CHARSET);
            RequestConfig requestConfig = RequestConfig.custom() // 设置超时
                    .setSocketTimeout(1000).setConnectTimeout(1000).build();
            httpget.setConfig(requestConfig);
            CloseableHttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY
                    ||
                    statusLine.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
                    || statusLine.getStatusCode() == HttpStatus.SC_SEE_OTHER
                    || statusLine.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
                Header header = httpget.getFirstHeader("location");
                if (header != null) {
                    String newUrl = header.getValue();
                    if (newUrl == null || newUrl.equals("")) {
                        newUrl = "/";
                        HttpGet redirect = new HttpGet(newUrl);
                    }
                }
            } else if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                if (entity == null) {
                    // throw new ClientProtocolException(
                    // "Response contains no content");
                } else {
                    System.out.println("处理URL: " + url);
                    String type = entity.getContentType().getValue();
                    if (type.contains("word") || type.contains("pdf")) {
                        System.out.println("发现一个附件: " + url);
                        if (null != entity.getContent())
                            extractAndIndexAttachment(url, type, entity.getContent());
                    } else {
                        //TODO
                        //extract links here
                        Set<String> set = HtmlParserTool.extractLinks(url, linkFilter);
                        for (String s : set) {
                            addToUncrawl(s);
                            addRelation(s, url);
                        }
                        if (count > 0) {
                            synchronized (lock) {
                                count--;//唤醒等待线程
                                lock.notifyAll();
                            }
                        }
                    }
                    removeRelation(url);
                }
            }
        } catch (ConnectTimeoutException e) {
            throw e;
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (UnknownHostException e) {
            throw e;
        }

    }

    private synchronized void addRelation(String child, String parent) {
        pageRelation.put(child, parent);
    }

    private synchronized void removeRelation(String child) {
        pageRelation.remove(child);
    }

    private String getTitle(String parentUrl) {
        return null;
    }

    private synchronized String getNext() {
        if (!unCrawlLinks.isEmpty()) {
            return unCrawlLinks.removeFirst();
        }
        return null;
    }

    private synchronized void addToUncrawl(String url) {
        if (!crawledLinks.contains(url) && !unCrawlLinks.contains(url)) {
            unCrawlLinks.addLast(url);
        }
    }

    private synchronized void addToCrawled(String url) {
        if (!crawledLinks.contains(url)) {
            crawledLinks.add(url);
        }
    }

    private void extractAndIndexAttachment(String url, String type, InputStream is) {
        String content = null;
        try {
            content = AttachmentUtil.extractContent(is);
            String parentUrl = pageRelation.get(url);
            AttachmentPage ap = new AttachmentPage();
            ap.setUrl(url);
            ap.setParentPage(parentUrl);
            ap.setContent(content);
            ap.setType(type);
            ap.setTitle(getTitle(parentUrl));
            ap.setCreateTime(new Date());
            System.out.println(ap.toString());
            index(ap);
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void index(AttachmentPage ap) {
        Indexer indexer = SearchFactory.getIndexer();
        try {
            indexer.index(ap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}
