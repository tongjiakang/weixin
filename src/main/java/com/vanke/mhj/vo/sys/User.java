package com.vanke.mhj.vo.sys;

import java.util.Date;
import java.util.List;

import com.vanke.mhj.utils.contants.UseStatus;
import com.vanke.mhj.vo.base.BaseType;
import com.vanke.mhj.vo.base.BaseVo;

public class User extends BaseVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -423651589252324166L;
	private String loginname; // 登录名
	private String password; // 密码
	private String userName; // 姓名
	private Integer sex; // 性别
	private Integer age; // 年龄
	private Integer isdefault; // 是否默认
	private Integer status; // 状态1启用-1停用
	private String statusLabel;
	private List<BaseType> statusList = UseStatus.getUseStatusList();

	private String phone;

	private Long orgId;
	private String orgNo;
	private String orgName;
	
	private Long areaId;
	private String areaName;
	
	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	private Date createdatetimeStart;
	private Date createdatetimeEnd;
	
	private Long roleId;
	private String roleName;
	private String roleType;
	
	public String getRoleType()
	{
		return roleType;
	}

	public void setRoleType(String roleType)
	{
		this.roleType = roleType;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<BaseType> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<BaseType> statusList) {
		this.statusList = statusList;
	}

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

}