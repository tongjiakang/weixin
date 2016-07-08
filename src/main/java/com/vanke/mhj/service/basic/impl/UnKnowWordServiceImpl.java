package com.vanke.mhj.service.basic.impl;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.vanke.mhj.dao.basic.AccountDao;
import com.vanke.mhj.dao.basic.UnKnowWordDao;
import com.vanke.mhj.model.basic.Account;
import com.vanke.mhj.model.basic.TUnKnowWord;
import com.vanke.mhj.model.material.MsgNews;
import com.vanke.mhj.model.material.NewsTemplate;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.basic.UnKnowWordService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.basic.VUnKnowWord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/6/29.
 */
@Service
public class UnKnowWordServiceImpl implements UnKnowWordService{

    @Resource
    private UnKnowWordDao unKnowWordDao;
    @Resource
    private AccountDao accountDao;

    /**
     * 列表
     * @param vUnKnowWord
     * @param ph
     * @return
     * @throws Exception
     */
    @Override
    public PageModel dataGrid(VUnKnowWord vUnKnowWord, PageModel ph) throws Exception {
        List<Condition> conditions = new ArrayList<Condition>();
        if (vUnKnowWord.getAccountId()!=null) {
			conditions.add(new Condition("account.id", vUnKnowWord.getAccountId(), Condition.EQUAL_TO));
		}
        QueryResult<TUnKnowWord> result = unKnowWordDao.getPageResult(TUnKnowWord.class, conditions, ph);
        List<VUnKnowWord> vUnKnowWordList = new ArrayList<VUnKnowWord>();
        for (TUnKnowWord tUnKnowWord : result.getReultList()) {
            VUnKnowWord unKnowWord = new VUnKnowWord();
            BeanUtils.copyProperties(tUnKnowWord, unKnowWord);
            String msgType=null;
            String templateName = null;
            if (unKnowWord.getMsgType().equals("text")) {
                msgType = "文本消息";
                templateName = unKnowWordDao.find(tUnKnowWord.getMsgId(), MsgNews.class).getName();
            }else if (unKnowWord.getMsgType().equals("news")) {
                msgType = "图文消息";
                templateName = unKnowWordDao.find(tUnKnowWord.getMsgId(), NewsTemplate.class).getTemplateName();
            }
            unKnowWord.setAccountName(tUnKnowWord.getAccount().getName());
            unKnowWord.setMsgType(msgType);
            unKnowWord.setTemplateName(templateName);
            vUnKnowWordList.add(unKnowWord);
        }
        ph.setRows(vUnKnowWordList);
        ph.setTotal(result.getTotalCount());
        return ph;
    }

    /**
     * 新增
     * @param vUnKnowWord
     * @throws Exception
     */
    @Override
    public void save(VUnKnowWord vUnKnowWord) throws Exception {
        //String appId = vUnKnowWord.getAppId();
        //Account account = accountDao.getAccountByAppId(appId);
    	Account account = accountDao.find(vUnKnowWord.getAccountId(), Account.class);
    	if (account == null) {
            throw new ServiceException(ControllerMsg.ACCOUNT_NOT_EXIST);
        }
        //判断该公众号是否已经设置回复语,每个公众号只能设置一条未识别回复语
        boolean flag = unKnowWordDao.hasAccount(account.getAppid());
        if (flag) {
            throw new ServiceException(ControllerMsg.UNKNOWWORK_ACCOUNT_EXIST);
        }
        TUnKnowWord tUnKnowWord = new TUnKnowWord();
        BeanUtils.copyProperties(vUnKnowWord, tUnKnowWord);
        tUnKnowWord.setAccount(account);
        unKnowWordDao.save(tUnKnowWord);
    }

    @Override
    public VUnKnowWord getVUnKnowWord(Long id) throws Exception {
        TUnKnowWord tUnKnowWord = unKnowWordDao.find(id, TUnKnowWord.class);
        VUnKnowWord unKnowWord = new VUnKnowWord();
        BeanUtils.copyProperties(tUnKnowWord, unKnowWord);
        unKnowWord.setAppId(tUnKnowWord.getAccount().getAppid());
        unKnowWord.setAccountId(tUnKnowWord.getAccount().getId());
        unKnowWord.setAccountName(tUnKnowWord.getAccount().getName());
        return unKnowWord;
    }

    @Override
    public void edit(VUnKnowWord vUnKnowWord) throws Exception {
        //String appId = vUnKnowWord.getAppId();
        //Account account = accountDao.getAccountByAppId(appId);
    	Account account = accountDao.find(vUnKnowWord.getAccountId(), Account.class);
        if (account == null) {
            throw new ServiceException(ControllerMsg.ACCOUNT_NOT_EXIST);
        }
        Long id = vUnKnowWord.getId();
        TUnKnowWord tUnKnowWord = unKnowWordDao.find(id, TUnKnowWord.class);
        if (!account.getAppid().equals(tUnKnowWord.getAccount().getAppid())) {
            //判断该公众号是否已经设置回复语,每个公众号只能设置一条未识别回复语
            boolean flag = unKnowWordDao.hasAccount(account.getAppid());
            if (flag) {
                throw new ServiceException(ControllerMsg.UNKNOWWORK_ACCOUNT_EXIST);
            }
        }
        tUnKnowWord.setMsgId(vUnKnowWord.getMsgId());
        tUnKnowWord.setMsgType(vUnKnowWord.getMsgType());
        tUnKnowWord.setAccount(account);
        unKnowWordDao.update(tUnKnowWord);
    }

    @Override
    public void delete(Long id) throws Exception {
        TUnKnowWord tUnKnowWord = unKnowWordDao.find(id, TUnKnowWord.class);
        unKnowWordDao.delete(tUnKnowWord);
    }
}
