package com.vanke.mhj.service.basic.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.vanke.mhj.dao.BaseDao;
import com.vanke.mhj.model.basic.Account;
import com.vanke.mhj.model.basic.Welcomes;
import com.vanke.mhj.model.material.MsgNews;
import com.vanke.mhj.model.material.NewsTemplate;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.basic.WelcomesService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.vo.basic.WelcomesVo;

@Service
public class WelcomesServiceImpl implements WelcomesService{
	
	@Resource
	private BaseDao baseDao;

	@Override
	public PageModel dataGrid(WelcomesVo welcomesVo, PageModel ph) {
		List<Condition> conditions = new ArrayList<Condition>();
		if (!StringUtils.isEmpty(welcomesVo.getAccountName())) {
			conditions.add(new Condition("account.name", "%"+welcomesVo.getAccountName()+"%", Condition.LIKE));
		}
		if (welcomesVo.getOrgId() !=null) {
			conditions.add(new Condition("organization.orgNo", "%"+baseDao.find(welcomesVo.getOrgId(), Torganization.class).getOrgNo().toLowerCase()+"%", Condition.LIKE));
		}
		if (welcomesVo.getAccountId()!=null) {
			conditions.add(new Condition("account.id", welcomesVo.getAccountId(), Condition.EQUAL_TO));
		}
		QueryResult<Welcomes> result = baseDao.getPageResult(Welcomes.class, conditions, ph);
		List<WelcomesVo> welcomesVos = new ArrayList<WelcomesVo>();
		for (Welcomes welcomes : result.getReultList()) {
			WelcomesVo wVo = new WelcomesVo();
			BeanUtils.copyProperties(welcomes, wVo);
			String msgType=null;
			String msgName = null;
			if (wVo.getMsgType().equals("text")) {
				MsgNews msgNews = baseDao.find(wVo.getMsgId(), MsgNews.class);
				msgType = "文本消息";
				if(msgNews!=null){
					msgName = msgNews.getName();
				}
			}else if (wVo.getMsgType().equals("news")) {
				NewsTemplate newsTemplate = baseDao.find(wVo.getMsgId(), NewsTemplate.class);
				msgType = "图文消息";
				if(newsTemplate!=null){
					msgName = newsTemplate.getTemplateName();
				}
			}
			wVo.setMsgType(msgType);
			wVo.setMsgName(msgName);
			wVo.setOrgName(welcomes.getOrganization().getOrgName());
			wVo.setAccountName(welcomes.getAccount().getName());
			wVo.setAccountId(welcomes.getAccount().getId());
			welcomesVos.add(wVo);
		}
		ph.setRows(welcomesVos);
		ph.setTotal(result.getTotalCount());
		return ph;
	}

	@Override
	public void save(WelcomesVo welcomesVo) {
		//判断该公众号是否已经设置回复语,每个公众号只能设置一条未识别回复语
        Welcomes w = this.getWelcomesByAccountId(welcomesVo.getAccountId());
        if (w!=null) {
            throw new ServiceException(ControllerMsg.WELCOME_ACCOUNT_EXIST);
        }
		Welcomes welcomes = new Welcomes();
		BeanUtils.copyProperties(welcomesVo,welcomes);
		welcomes.setOrganization(baseDao.find(welcomesVo.getOrgId(), Torganization.class));
		welcomes.setAccount(baseDao.find(welcomesVo.getAccountId(), Account.class));
		baseDao.save(welcomes);
	}

	@Override
	public WelcomesVo getWelcomes(Long id) {
		Welcomes welcomes = baseDao.find(id, Welcomes.class);
		WelcomesVo welcomesVo = new WelcomesVo();
		BeanUtils.copyProperties(welcomes,welcomesVo);
		welcomesVo.setAccountId(welcomes.getAccount().getId());
		welcomesVo.setAccountName(welcomes.getAccount().getName());
		return welcomesVo;
	}

	@Override
	public void update(WelcomesVo welcomesVo) {
		Welcomes welcomes = baseDao.find(welcomesVo.getId(), Welcomes.class);
		if (!welcomes.getAccount().getId().equals(welcomesVo.getAccountId())) {
            //判断该公众号是否已经设置回复语,每个公众号只能设置一条未识别回复语
			Welcomes w = this.getWelcomesByAccountId(welcomesVo.getAccountId());
	        if (w!=null) {
	            throw new ServiceException(ControllerMsg.WELCOME_ACCOUNT_EXIST);
	        }
        }
		welcomes.setUpdateUser(welcomesVo.getUpdateUser());
		welcomes.setUpdateDate(welcomesVo.getUpdateDate());
		welcomes.setAccount(baseDao.find(welcomesVo.getAccountId(), Account.class));
		welcomes.setMsgType(welcomesVo.getMsgType());
		welcomes.setMsgId(welcomesVo.getMsgId());
		baseDao.update(welcomes);
	}

	@Override
	public void delete(String ids) {
		String[] idArray = ids.split(",");
		List<Welcomes> list = new ArrayList<Welcomes>();
		for (int i = 0; i < idArray.length; i++) {
			Welcomes welcomes = new Welcomes();
			welcomes.setId(Long.valueOf(idArray[i]));
			list.add(welcomes);
		}
		baseDao.delete(list);
	}

	@Override
	public Welcomes getWelcomesByAccountId(Long id) {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("account.id",id, Condition.EQUAL_TO));
		return baseDao.getSingleResult(Welcomes.class, conditions);
	}

}
