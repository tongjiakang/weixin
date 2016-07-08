package com.vanke.mhj.vo.base;

import java.util.List;

public class SessionInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 868479274763641841L;
	private Long id;// 用户ID
	private String loginname;// 登录名
	private String username;// 姓名
	private String ip;// 用户IP
	private String orgNo;// 机构编号
	private Long orgId;// 机构ID
	private Long areaId;// 区域ID

	private List<String> resourceList;// 用户可以访问的资源地址列表

	private List<String> resourceAllList;
	
	private Long roleId;
	private String roleType;
	
	public String getRoleType()
	{
		return roleType;
	}

	public void setRoleType(String roleType)
	{
		this.roleType = roleType;
	}

	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getId() {
		return id;
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

	public List<String> getResourceAllList() {
		return resourceAllList;
	}

	public void setResourceAllList(List<String> resourceAllList) {
		this.resourceAllList = resourceAllList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

}
