package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * PAD基本信息表
 * ClassName: PadBaseinfo 
 * @Description: TODO
 * @author zhangqing
 * @date 2015-3-20
 */
public class PadBaseinfo {
	
	 private Integer sid;
	 private String padNo;//pad编号
	 private String macAddress;//MAC地址
	 private String purchaseOrderno;//采购订单号
	 private Integer padStatus;//pad状态 0在库 1卖场 2送修3停用4丢失
	 private Integer useType;//使用类型 0：导购；1：主管；2：内衣功能区；3：大场
	 private String useTypeDesc;//使用类型描述
	 private Date createTime;//创建时间
	 private String brand;//PAD品牌
	 private String operator;//操作人姓名
	 private Integer operatorSid;//操作人sid
	 private String padPurchaseBatchNo;//批次号
	 
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
	@Override
	public String toString() {
		return "PadBaseinfo [sid=" + sid + ", padNo=" + padNo + ", macAddress=" + macAddress + ", purchaseOrderno="
				+ purchaseOrderno + ", padStatus=" + padStatus + ", useType=" + useType + ", useTypeDesc=" + useTypeDesc
				+ ", createTime=" + createTime + ", brand=" + brand + ", operator=" + operator + ", operatorSid="
				+ operatorSid + ", padPurchaseBatchNo=" + padPurchaseBatchNo + "]";
	}
	
}
