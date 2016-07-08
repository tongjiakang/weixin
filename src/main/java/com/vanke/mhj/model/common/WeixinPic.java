package com.vanke.mhj.model.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "WX_MSG_TEXTPIC")
@Entity
public class WeixinPic {
    @Id
    @Column(name = "PICID", length = 32)
    private String picId;
    @Column(name = "PICURL", length = 512)
    private String picUrl;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
