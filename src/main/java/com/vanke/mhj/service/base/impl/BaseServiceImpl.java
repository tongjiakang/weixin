package com.vanke.mhj.service.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vanke.mhj.dao.BaseDao;
import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.service.base.BaseService;

/**
 * 2016-02-06
 * 
 * @author AlawnPang
 *
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Resource
    private BaseDao baseDao;

    @Override
    public String callBizSeqCode(String seqType) throws Exception {
        return baseDao.callBizSeqCode(seqType);
    }

    @Override
    public <T> T find(Object id, Class<T> clazz) {
        return baseDao.find(id, clazz);
    }

    @Override
    public <T> void save(T t) {
        baseDao.save(t);
    }

    @Override
    public <T extends IdEntity> void update(T t) {
        baseDao.update(t);

    }

    @Override
    public <T> void delete(T t) {
        baseDao.delete(t);

    }

}
