package com.vanke.mhj.utils.contants;

public enum BizSeqType {

	WL("WL", "物料"), CP("CP", "产品"), XM("XM", "项目"), HX("HX", "户型"), FW("FW", "房屋"), YZ("YZ", "业主"), FA("FA",
			"方案"), XSDD("XSDD", "销售订单"), SKD("SKD", "收款单"), CGD("CGD", "采购单"), PSD("PSD", "配送单"), THD("THD",
					"退货单"), GYS("GYS", "供应商"), SHD("SHD", "收货单"), HT("HT", "合同"), SGD("SGD", "施工单"),YHCL("YHCL","优惠策略");

	private String seqType;
	private String description;

	private BizSeqType(String seqType, String description) {
		this.seqType = seqType;
		this.description = description;
	}

	public String getSeqType() {
		return seqType;
	}

	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
