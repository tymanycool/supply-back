package net.shopin.supply.domain.vo;

import java.util.Date;

/**
 * Title: PadInfoCheckVO
 * Description: 信息查看模块VO类
 * @author SunYukun
 * @date 2016年4月1日 上午10:18:54
 */
public class PadInfoCheckVO {
	private Integer sid;
	private String padPurchaseBatchNo;// 批次号
	private String padNo;// pad编号
	private String macAddress;// MAC地址
	private String padSupply; // PAD厂商
	private String padBrand; // PAD品牌
	private Date padPurchaseTime; // PAD订购时间
	private Integer padStatus;// pad状态 0在库 1卖场 2送修3停用
	private Integer useType;// 使用类型 0：导购；1：主管；2：内衣功能区；3：大场
	private String useTypeDesc;// 使用类型描述
	private Date createTime;// 创建时间
	private String supplyName;// 供应商名称
	private Integer shopId;// 门店ID
	private String shopName;// 门店名称
	private Integer targetShopId;
	private String targetShopName;// 目的门店名称
	private String operator;// 操作人姓名
	private Integer operatorSid;// 操作人sid

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getPadPurchaseBatchNo() {
		return padPurchaseBatchNo;
	}

	public void setPadPurchaseBatchNo(String padPurchaseBatchNo) {
		this.padPurchaseBatchNo = padPurchaseBatchNo;
	}

	public String getPadNo() {
		return padNo;
	}

	public void setPadNo(String padNo) {
		this.padNo = padNo;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getPadSupply() {
		return padSupply;
	}

	public void setPadSupply(String padSupply) {
		this.padSupply = padSupply;
	}

	public String getPadBrand() {
		return padBrand;
	}

	public void setPadBrand(String padBrand) {
		this.padBrand = padBrand;
	}

	public Date getPadPurchaseTime() {
		return padPurchaseTime;
	}

	public void setPadPurchaseTime(Date padPurchaseTime) {
		this.padPurchaseTime = padPurchaseTime;
	}

	public Integer getPadStatus() {
		return padStatus;
	}

	public void setPadStatus(Integer padStatus) {
		this.padStatus = padStatus;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public String getUseTypeDesc() {
		return useTypeDesc;
	}

	public void setUseTypeDesc(String useTypeDesc) {
		this.useTypeDesc = useTypeDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getOperatorSid() {
		return operatorSid;
	}

	public void setOperatorSid(Integer operatorSid) {
		this.operatorSid = operatorSid;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getTargetShopId() {
		return targetShopId;
	}

	public void setTargetShopId(Integer targetShopId) {
		this.targetShopId = targetShopId;
	}

	public String getTargetShopName() {
		return targetShopName;
	}

	public void setTargetShopName(String targetShopName) {
		this.targetShopName = targetShopName;
	}
}
