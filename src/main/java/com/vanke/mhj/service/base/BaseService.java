package com.vanke.mhj.service.base;

import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.model.material.NewsItem;

/**
 * 2016-02-05
 * 
 * @author AlawnPang
 *
 */
public interface BaseService {

    /**
     * 获取业务数据序列编号
     * 
     * @param seqType
     * @return
     */
    public String callBizSeqCode(String seqType) throws Exception;

    public <T> T find(Object id, Class<T> clazz);

    public <T> void save(T t);

    public <T extends IdEntity> void update(T t);

    public <T> void delete(T t);

}
