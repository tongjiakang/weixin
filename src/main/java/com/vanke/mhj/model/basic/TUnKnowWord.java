package com.vanke.mhj.model.basic;

import com.vanke.mhj.model.base.IdEntity;

import javax.persistence.*;

/**
 * Created by PanJM on 2016/6/29.
 * 未识别回复语
 */
@Entity
@Table(name="wx_account_tunknowword")
public class TUnKnowWord extends IdEntity{

    @Column(name = "MSGTYPE", length = 10)
    private String msgType;
    @Column(name = "MSGID", length = 10)
    private Long msgId;

    @JoinColumn(name="APP_ID",referencedColumnName="appid")
    @ManyToOne(fetch=FetchType.LAZY)
    private Account account;

    public TUnKnowWord() {
    }

    public TUnKnowWord(Long id) {
        this.id = id;
    }

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
