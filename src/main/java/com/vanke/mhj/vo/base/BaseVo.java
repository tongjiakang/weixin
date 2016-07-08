package com.vanke.mhj.vo.base;

import java.util.Date;

public class BaseVo {
	protected Long id;

	protected String createUser;

	protected Date createDate;

	protected String updateUser;

	protected Date updateDate;
	
	private SessionInfo sessionInfo;
	
	public SessionInfo getSessionInfo()
	{
		return sessionInfo;
	}

	public void setSessionInfo(SessionInfo sessionInfo)
	{
		this.sessionInfo = sessionInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateUser() {
		if(sessionInfo != null) {
			return sessionInfo.getLoginname();
		}
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		if(createDate == null) {
			createDate = new Date();
		}
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		if(sessionInfo != null) {
			return sessionInfo.getLoginname();
		}
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		if (this.updateDate == null) {
			this.updateDate = new Date();
		}
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}