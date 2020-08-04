package indi.df.fmall.bean;

import java.io.Serializable;
import java.util.List;

//搜索时传递进来的参数，用PmsSearchParam来接收catalog3Id、keyword、valueId
public class PmsSearchParam implements Serializable{

    private String catalog3Id;

    private String keyword;

    private String[] valueId; //属性值id存在数组里，以实现面包屑功能

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String[] getValueId() {
        return valueId;
    }

    public void setValueId(String[] valueId) {
        this.valueId = valueId;
    }
}

