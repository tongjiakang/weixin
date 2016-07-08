package com.vanke.mhj.vo.material;

import com.vanke.mhj.utils.contants.UseStatus;
import com.vanke.mhj.vo.base.BaseVo;

public class VPicNewsTemplate extends BaseVo {
    private Long orgId;
    private String templateName;
    private String name;
    private Integer useStatus= UseStatus.ACTIVITY.getValue();
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String type;
    private String orgName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
