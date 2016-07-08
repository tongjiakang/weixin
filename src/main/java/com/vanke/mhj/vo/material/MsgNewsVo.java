package com.vanke.mhj.vo.material;

import com.vanke.mhj.utils.contants.UseStatus;
import com.vanke.mhj.vo.base.BaseVo;

public class MsgNewsVo extends BaseVo{
	
	private String name;
	
	private String content;
	
	private String orgName;
	private Long orgId;
    private String orgNo;
    private Integer useStatus= UseStatus.ACTIVITY.getValue();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

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

	/**
	 * @return the userStatus
	 */
	public Integer getUseStatus() {
		return useStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	
}
