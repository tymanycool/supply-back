package net.shopin.supply.domain.vo;

import java.util.Date;

public class PadBaseinfoVO{
	 private Integer sid;
	 private String padNo;//pad编号
	 private String macAddress;//MAC地址
	 private String purchaseOrderno;//采购订单号
	 private Integer padStatus;//pad状态 0在库 1卖场 2送修3停用
	 private Integer useType;//使用类型 0：导购；1：主管；2：内衣功能区；3：大场
	 private String useTypeDesc;//使用类型描述
	 private Date createTime;//创建时间
	 private String brand;//PAD品牌
	 private String supplyName;//供应商名称
	 private String shopName;//门店名称
	 private String operator;//操作人姓名
	 private Integer operatorSid;//操作人sid
	 //add by qutengfei for 查看pad安装那些软件 in 20150723 start
	 private String padStatusTotal; //某状态PAD的个数
	 /**
	 * 查看pad安装列表
	 * for bug
	 * feature https://tower.im/s/9cCdh
	 * author qutengfei
	 */
	 private Integer shopId;//门店ID
	 //add by qutengfei for 查看pad安装那些软件 in 20150723 end
	 private String padPurchaseBatchNo;// 批次号
	 private Integer targetShopId;
	 private String targetShopName;//目的门店名称
	 
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

	public String getPadPurchaseBatchNo() {
		return padPurchaseBatchNo;
	}

	public void setPadPurchaseBatchNo(String padPurchaseBatchNo) {
		this.padPurchaseBatchNo = padPurchaseBatchNo;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getPurchaseOrderno() {
		return purchaseOrderno;
	}
	public void setPurchaseOrderno(String purchaseOrderno) {
		this.purchaseOrderno = purchaseOrderno;
	}
	public Integer getPadStatus() {
		return padStatus;
	}
	public void setPadStatus(Integer padStatus) {
		this.padStatus = padStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
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
	public String getPadNo() {
		return padNo;
	}
	public void setPadNo(String padNo) {
		this.padNo = padNo;
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

	public String getPadStatusTotal() {
		return padStatusTotal;
	}

	public void setPadStatusTotal(String padStatusTotal) {
		this.padStatusTotal = padStatusTotal;
	}

	
}