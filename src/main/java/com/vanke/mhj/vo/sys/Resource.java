package com.vanke.mhj.vo.sys;

import java.util.List;

import com.vanke.mhj.utils.contants.UseStatus;
import com.vanke.mhj.vo.base.BaseType;
import com.vanke.mhj.vo.base.BaseVo;

public class Resource extends BaseVo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1629675936490311498L;
	private Long pid;
	private String pname;

	private String name; // 名称
	private String url; // 菜单路径
	private String description; // 描述
	private String iconCls; // 图标
	private Integer seq; // 排序号
	private Integer resourcetype; // 资源类型, 0菜单 1功能
	private Integer status; // 状态 1启用 -1停用
	private String StatusLabel;

	private List<BaseType> statusList = UseStatus.getUseStatusList();// 使用状态

	public Resource() {
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getResourcetype() {
		return resourcetype;
	}

	public void setResourcetype(Integer resourcetype) {
		this.resourcetype = resourcetype;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusLabel() {
		return StatusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		StatusLabel = statusLabel;
	}

	public List<BaseType> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<BaseType> statusList) {
		this.statusList = statusList;
	}

}