package org.sina.data;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jerold on 2016/11/15.
 */
public interface Extractor {
    void extract(InputStream in) throws IOException;
}
