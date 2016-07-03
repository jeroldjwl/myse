package org.shyp.parser;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.shyp.search.LinkFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jerold on 16/6/2.
 */
public class HtmlParserTool {
    public static Set<String> extractLinks(String url, LinkFilter filter) {
        Set<String> links = new HashSet<String>();
        try {
            Parser parser = null;
            try {
                parser = new Parser(url);
            } catch (ParserException e) {
                return null;
            }
            parser.setEncoding("utf-8");

            NodeFilter frameFilter = new NodeFilter() { // 过滤节点
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

            OrFilter linkFilter = new OrFilter(new NodeClassFilter(
                    LinkTag.class), frameFilter);
            NodeList list = parser.extractAllNodesThatMatch(linkFilter);
            for (int i = 0; i < list.size(); i++) {
                Node tag = list.elementAt(i);
                if (tag instanceof LinkTag) { // 链接文字
                    LinkTag linkTag = (LinkTag) tag;
                    String linkUrl = linkTag.getLink();// url
                    String text = linkTag.getLinkText();// 链接文字
                    //System.out.println("URL: " + linkUrl + " 文字: " + text);
                    if (filter.accept(linkUrl))
                        links.add(linkUrl);
                } else// <frame> 标签
                {
                    // 提取 frame 里 src 属性的链接如 <frame src="test.html"/>
                    String frame = tag.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if (end == -1)
                        end = frame.indexOf(">");
                    frame = frame.substring(5, end - 1);
                    System.out.println(frame);
                    if (filter.accept(frame))
                        links.add(frame);
                }
            }

            return links;
        } catch (ParserException e) {
            e.printStackTrace();
            return null;
        }
    }
}
