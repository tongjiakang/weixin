package com.vanke.wxapi.vo;

import java.util.List;

/**
 * Created by PanJM on 2016/7/7.
 */
public class GroupSendByOpenId {

    private List<String> touser;

    private MpNews mpnews;

    private String msgtype;

    public List<String> getTouser() {
        return touser;
    }

    public void setTouser(List<String> touser) {
        this.touser = touser;
    }

    public MpNews getMpnews() {
        return mpnews;
    }

    public void setMpnews(MpNews mpnews) {
        this.mpnews = mpnews;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
