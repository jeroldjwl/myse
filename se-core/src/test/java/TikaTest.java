import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.shyp.crawler.impl.CrawlerImpl;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jerold on 16/5/29.
 */
public class TikaTest {

    public static void main(String[] args) {
        try {
            test2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test1() throws TikaException, IOException, SAXException {
        CrawlerImpl crawler = new CrawlerImpl();
        //crawler.download("http://www.shyp.gov.cn/xxgk/ReadAttachFile.aspx?AttachGuid=9478f333-75d5-4682-8eb3-84e54059c2a7");
    }

    private static void test2() {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
        ParseContext context = new ParseContext();
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File("/Users/jerold/Desktop/网上商城项目概要.doc"));
            parser.parse(stream, handler, metadata, context);
            String result = handler.toString();
            System.out.println(result);
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
