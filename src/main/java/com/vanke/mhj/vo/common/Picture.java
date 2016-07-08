package com.vanke.mhj.vo.common;

import com.vanke.mhj.vo.base.BaseVo;

public class Picture extends BaseVo {
	private String picName;

	private String picDirPath;

	private String saveFileName;

	private Integer seq;

	private String bizNo;

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicDirPath() {
		return picDirPath;
	}

	public void setPicDirPath(String picDirPath) {
		this.picDirPath = picDirPath;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
}
