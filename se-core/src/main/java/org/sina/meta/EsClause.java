package org.sina.meta;

/**
 * Created by Jerold on 2016/11/29.
 */
public class EsClause {
    private String name;
    private String value;

    public EsClause(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
