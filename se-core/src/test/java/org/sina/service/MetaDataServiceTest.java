package org.sina.service;

import org.junit.Test;

/**
 * Created by Jerold on 2016/11/20.
 */
public class MetaDataServiceTest {
    @Test
    public void generateSettingsTest() {
        MetaDataService mds = new MetaDataService();
        String settings = mds.generateSettings();
        System.out.println(settings);
    }

    @Test
    public void generateMappingTest() {
        MetaDataService mds = new MetaDataService();
        String mapping = mds.generateMappings("users");
        System.out.println(mapping);
    }
}
