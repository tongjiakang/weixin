package com.vanke.mhj.dao.util;

import java.util.List;

public class SqlBuilder {
    private String sql;
    private List<SqlParam> params;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<SqlParam> getParams() {
        return params;
    }

    public void setParams(List<SqlParam> params) {
        this.params = params;
    }

}
