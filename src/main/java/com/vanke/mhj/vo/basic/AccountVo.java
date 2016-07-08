package com.vanke.mhj.vo.basic;

import com.vanke.mhj.vo.base.BaseVo;

public class AccountVo extends BaseVo{

	private Long orgId;
	private String orgNo;
	private String orgName;
	
	/**
	 * 公众号名称
	 */
	private String name;
	
	/**
	 * 公众号APPID
	 */
	private String appid;
	
	/**
	 * 公众号识别码
	 */
	private String appsecret;
	
	/**
	 * 公众号TOKEN
	 */
	private String token;
	
	/**
	 * TOKEN加密串
	 */
	private String encodingAESKey;
	
	/**
	 * 接受消息地址
	 */
	private String receiverAddress;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	
}
