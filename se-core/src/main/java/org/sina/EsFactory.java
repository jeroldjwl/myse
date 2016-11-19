package org.sina;

import org.sina.adapter.EsAdapter;
import org.sina.adapter.impl.EsAdpaterJavaImpl;

/**
 * Created by Jerold on 2016/11/19.
 */
public class EsFactory {

    public static EsAdapter getEsAdapter(int operation) {
        if (operation == 2) {
            return new EsAdpaterJavaImpl();
        } else {
            return new EsAdpaterJavaImpl();
        }
    }
}
