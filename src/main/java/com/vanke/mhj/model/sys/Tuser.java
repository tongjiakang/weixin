package com.vanke.mhj.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.vanke.mhj.model.base.IdEntity;

@Entity
@Table(name = "sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tuser extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5809909433202760738L;
	@Column(name = "LOGINNAME", length = 20, unique = true)
	private String loginname; // 登录名

	@Column(name = "USERNAME", length = 20)
	private String userName; // 姓名

	@Column(name = "PASSWORD", length = 50)
	private String password; // 密码

	@Column(name = "SEX", length = 3)
	private Integer sex; // 性别

	@Column(name = "AGE", length = 3)
	private Integer age; // 年龄
	
	@Column(name = "OPENID",length=50)
	private String openId;	//微信openId
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * 机构编号
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_NO", referencedColumnName = "ORG_NO")
	private Torganization organization;

	@Column(name = "ISDEFAULT", length = 3)
	private Integer isdefault; // 是否默认

	@Column(name = "PHONE", length = 20, unique = true)
	private String phone;

	@Column(name = "STATUS", length = 3)
	private Integer status; // 状态


	public Tuser() {
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Torganization getOrganization() {
		return organization;
	}

	public void setOrganization(Torganization organization) {
		this.organization = organization;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}