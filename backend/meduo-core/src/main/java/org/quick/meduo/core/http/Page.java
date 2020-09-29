package org.quick.meduo.core.http;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonObject;

public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page {
    private T params;
    public void setPageSize(long size){
        this.setSize(size);
    }

    public void setPageNo(long no){
        this.setCurrent(no);
    }

    public void setTotalCount(long total){
        this.setTotal(total);
    }

    public long getPageSize(){
        return this.getSize();
    }

    public long getPageNo(){
        return this.getCurrent();
    }

    public long getTotalCount(){
        return this.getTotal();
    }

    @Override
    @JsonIgnore
    public List getRecords() {
        return super.getRecords();
    }

    public List getData(){
        return getRecords();
    }

    public void setData(List recorod){
        this.setRecords(recorod);
    }

    public void setParams(T params) {
        this.params = params;
    }

    public T getParams() {
        return params;
    }
}
