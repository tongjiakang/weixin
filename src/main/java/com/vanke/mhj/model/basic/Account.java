package com.vanke.mhj.model.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.model.sys.Torganization;


@Entity
@Table(name="WX_ACCOUNT")
public class Account extends IdEntity{

	private static final long serialVersionUID = -6955829787077047283L;
	
	/**
	 * 机构编号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_NO", referencedColumnName = "ORG_NO")
	private Torganization organization;
	
	/**
	 * 公众号名称
	 */
	@Column(name="NAME",length=50)
	private String name;
	
	/**
	 * 公众号APPID
	 */
	@Column(name="APPID",length=50)
	private String appid;
	
	/**
	 * 公众号识别码
	 */
	@Column(name="APPSECRET",length=50)
	private String appsecret;
	
	/**
	 * 公众号TOKEN
	 */
	@Column(name="TOKEN",length=20)
	private String token;
	
	/**
	 * TOKEN加密串
	 */
	@Column(name="ENCODINGAESKEY",length=100)
	private String encodingAESKey;
	
	/**
	 * 接受消息地址
	 */
	@Column(name="RECEIVERADDRESS",length=200)
	private String receiverAddress;

	public Torganization getOrganization() {
		return organization;
	}

	public void setOrganization(Torganization organization) {
		this.organization = organization;
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
