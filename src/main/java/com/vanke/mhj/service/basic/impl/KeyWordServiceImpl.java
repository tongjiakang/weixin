/**
 * 
 */
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
import com.vanke.mhj.dao.basic.KeyWordDao;
import com.vanke.mhj.model.basic.Account;
import com.vanke.mhj.model.basic.TKeyWord;
import com.vanke.mhj.model.material.MsgNews;
import com.vanke.mhj.model.material.NewsTemplate;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.service.basic.KeyWordService;
import com.vanke.mhj.vo.basic.VKeyWord;

/**
 * @author qianwei
 *
 */
@Service
public class KeyWordServiceImpl implements KeyWordService{
	
	@Resource
	private KeyWordDao keyWordDao;

	@Override
	public void save(VKeyWord vKeyWord) {
		TKeyWord tKeyWord = new TKeyWord();
		BeanUtils.copyProperties(vKeyWord, tKeyWord);
		tKeyWord.setOrganization(keyWordDao.find(vKeyWord.getOrgId(), Torganization.class));
		tKeyWord.setAccount(keyWordDao.find(vKeyWord.getAccountId(), Account.class));
		keyWordDao.save(tKeyWord);
	}

	@Override
	public PageModel dataGrid(VKeyWord vKeyWord, PageModel ph) {
		List<Condition> conditions = new ArrayList<Condition>();
		if (!StringUtils.isEmpty(vKeyWord.getName())) {
			conditions.add(new Condition("name", "%"+vKeyWord.getName().toLowerCase()+"%", Condition.LIKE));
		}
		if (vKeyWord.getAccountId()!=null) {
			conditions.add(new Condition("account.id", vKeyWord.getAccountId(), Condition.EQUAL_TO));
		}
		if (vKeyWord.getOrgId() !=null) {
			conditions.add(new Condition("organization.orgNo", "%"+keyWordDao.find(vKeyWord.getOrgId(), Torganization.class).getOrgNo().toLowerCase()+"%", Condition.LIKE));
		}
		QueryResult<TKeyWord> result = keyWordDao.getPageResult(TKeyWord.class, conditions, ph);
		List<VKeyWord> vKeyWords = new ArrayList<VKeyWord>();
		for (TKeyWord tKeyWord : result.getReultList()) {
			VKeyWord vKeyWord2 = new VKeyWord();
			BeanUtils.copyProperties(tKeyWord, vKeyWord2);
			String msgType=null;
			String templateName = null;
			if (vKeyWord2.getMsgType().equals("text")) {
				msgType = "文本消息";
				templateName = keyWordDao.find(tKeyWord.getMsgId(), MsgNews.class).getName();
			}else if (vKeyWord2.getMsgType().equals("news")) {
				msgType = "图文消息";
				templateName = keyWordDao.find(tKeyWord.getMsgId(), NewsTemplate.class).getTemplateName();
			}
			vKeyWord2.setAccountName(tKeyWord.getAccount().getName());
			vKeyWord2.setMsgType(msgType);
			vKeyWord2.setTemplateName(templateName);
			vKeyWord2.setOrgName(tKeyWord.getOrganization().getOrgName());
			vKeyWords.add(vKeyWord2);
		}
		ph.setRows(vKeyWords);
		ph.setTotal(result.getTotalCount());
		return ph;
	}

	@Override
	public VKeyWord getKeyWord(Long id) {
		TKeyWord tKeyWord = keyWordDao.find(id, TKeyWord.class);
		VKeyWord vKeyWord = new VKeyWord();
		BeanUtils.copyProperties(tKeyWord, vKeyWord);
		vKeyWord.setAccountId(tKeyWord.getAccount().getId());
		vKeyWord.setAccountName(tKeyWord.getAccount().getName());
		return vKeyWord;
	}

	@Override
	public void update(VKeyWord vKeyWord) {
		TKeyWord tKeyWord = keyWordDao.find(vKeyWord.getId(), TKeyWord.class);
		tKeyWord.setUpdateUser(vKeyWord.getUpdateUser());
		tKeyWord.setMsgId(vKeyWord.getMsgId());
		tKeyWord.setMsgType(vKeyWord.getMsgType());
		tKeyWord.setName(vKeyWord.getName());
		tKeyWord.setAccount(keyWordDao.find(vKeyWord.getAccountId(), Account.class));
		keyWordDao.update(tKeyWord);
	}

	@Override
	public void delete(Long id) {
		TKeyWord tKeyWord = keyWordDao.find( id, TKeyWord.class);
		keyWordDao.delete(tKeyWord);
	}
}
