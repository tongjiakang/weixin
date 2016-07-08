package com.vanke.mhj.service.basic.impl;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.google.common.collect.Lists;
import com.vanke.mhj.dao.basic.AccountDao;
import com.vanke.mhj.model.basic.Account;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.service.basic.AccountService;
import com.vanke.mhj.service.sys.OrganizationService;
import com.vanke.mhj.vo.base.Label;
import com.vanke.mhj.vo.basic.AccountVo;
import com.vanke.mhj.vo.sys.Organization;
import com.vanke.wxapi.process.AccessToken;
import com.vanke.wxapi.process.WxApi;
import com.vanke.wxapi.process.WxApiClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Resource
    private OrganizationService organizationService;

    @Override
    public PageModel dataGrid(AccountVo accountVo, PageModel ph) {
        List<Condition> conditionList = Lists.newArrayList();
        if (!StringUtils.isEmpty(accountVo.getOrgId())) {
            Organization org = organizationService.get(accountVo.getOrgId());
            accountVo.setOrgNo(org.getOrgNo());
            conditionList.add(new Condition("organization.orgNo", accountVo.getOrgNo().toLowerCase() + "%", Condition.LIKE));
        }
        if (!StringUtils.isEmpty(accountVo.getName())) {
            conditionList.add(new Condition("name", "%" + accountVo.getName() + "%", Condition.LIKE));
        }
        QueryResult<Account> pageResult = accountDao.getPageResult(Account.class, conditionList, ph);
        List<AccountVo> accountVos = new ArrayList<>();
        for (Account account : pageResult.getReultList()) {
            AccountVo v = new AccountVo();
            BeanUtils.copyProperties(account, v);
            Torganization org = account.getOrganization();
            if (org != null) {
                v.setOrgId(org.getId());
                v.setOrgNo(org.getOrgNo());
                v.setOrgName(org.getOrgName());
            }
            accountVos.add(v);
        }
        ph.setTotal(pageResult.getTotalCount());
        ph.setRows(accountVos);
        return ph;
    }

    @Override
    public void save(AccountVo accountVo) {
        Account account = new Account();
        BeanUtils.copyProperties(accountVo, account);
        account.setOrganization(accountDao.find(accountVo.getOrgId(), Torganization.class));
        accountDao.save(account);
        //更新access_token缓存
        Map<String, AccessToken> accessTokenMap = WxApiClient.getAccessTokenCache();
        accessTokenMap.put(account.getAppid(), WxApi.getAccessToken(account));
    }

    @Override
    public AccountVo getAccount(Long id) {
        Account account = accountDao.find(id, Account.class);
        AccountVo accountVo = new AccountVo();
        BeanUtils.copyProperties(account, accountVo);
        return accountVo;
    }

    @Override
    public void update(AccountVo accountVo) {
        Map<String, AccessToken> accessTokenMap = WxApiClient.getAccessTokenCache();
        Account account = accountDao.find(accountVo.getId(), Account.class);
        //如果appid变了,删除缓存中的数据
        if (!account.getAppid().equals(accountVo.getAppid())) {
            accessTokenMap.remove(account.getAppid());
        }
        account.setName(accountVo.getName());
        account.setAppid(accountVo.getAppid());
        account.setAppsecret(accountVo.getAppsecret());
        account.setEncodingAESKey(accountVo.getEncodingAESKey());
        account.setToken(accountVo.getToken());
        account.setReceiverAddress(accountVo.getReceiverAddress());
        account.setUpdateDate(accountVo.getUpdateDate());
        account.setUpdateUser(accountVo.getUpdateUser());
        accountDao.update(account);
        //更新access_token缓存
        accessTokenMap.put(account.getAppid(), WxApi.getAccessToken(account));
    }

    @Override
    public void delete(String ids) {
        String[] idArray = ids.split(",");
        List<Account> list = new ArrayList<Account>();
        for (int i = 0; i < idArray.length; i++) {
            Long id = Long.valueOf(idArray[i]);
            Account account = accountDao.find(id, Account.class);
            list.add(account);
            //删除缓存
            Map<String, AccessToken> accessTokenMap = WxApiClient.getAccessTokenCache();
            accessTokenMap.remove(account.getAppid());
        }
        accountDao.delete(list);
    }

    @Override
    public AccountVo getAccountByAppid(String appid) {
        List<Condition> conList = Lists.newArrayList();
        if (!StringUtils.isEmpty(appid)) {
            conList.add(new Condition("appid", appid, Condition.EQUAL_TO));
        }
        Account account = accountDao.getSingleResult(Account.class, conList);
        AccountVo accountVo = new AccountVo();
        if (account != null) {
            BeanUtils.copyProperties(account, accountVo);
        } else {
            return null;
        }
        return accountVo;
    }

    /**
     * 获取公众号列表
     */
    @Override
    public List<Label> getAcountsLabel(String orgNo) {
        String ql = "select t from Account t where t.organization.orgNo like :orgNo";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("orgNo", "%"+orgNo+"%");
        List<Account> accounts = accountDao.getObjects(ql, parameters);
        List<Label> labels = new ArrayList<Label>();
        int i = 0;
        for (Account account : accounts) {
            Label label = new Label();
            label.setId(account.getId());
            label.setText(account.getName());
            labels.add(label);
            i++;
        }
        return labels;
    }
}
