package com.vanke.mhj.vo.basic;

import com.vanke.mhj.vo.base.BaseVo;

/**
 * Created by PanJm on 2016/6/29.
 */
public class VUnKnowWord extends BaseVo {

    private String msgType;
    private Long msgId;
    private String templateName;
    private String appId;
    private Long accountId;
    private String accountName;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
    
}
