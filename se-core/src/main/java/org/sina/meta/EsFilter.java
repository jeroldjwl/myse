package org.sina.meta;

/**
 * Created by Jerold on 2016/11/26.
 */
public class EsFilter {
    private String field;
    private String type;
    private String operation;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
