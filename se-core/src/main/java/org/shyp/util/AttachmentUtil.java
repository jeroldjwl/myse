package org.shyp.util;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jerold on 16/5/29.
 */
public class AttachmentUtil {

    public static String extractContent(InputStream is) throws TikaException, SAXException, IOException {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
        parser.parse(is, handler, metadata);
        return handler.toString();
    }
}
