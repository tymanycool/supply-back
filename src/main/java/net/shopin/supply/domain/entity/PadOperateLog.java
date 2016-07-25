package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * PAD日志表_用于显示PAD整个情况
 * @author syk
 *
 */
public class PadOperateLog {

	private Integer sid;
	private String padNo;// pad编号
	private Date operatTime;// 操作时间
	private String operator;// 操作人姓名
	private Integer operatorId;
	private String description;// 操作描述
	private Integer shopId;
	private Integer targetShopId;
	private String shopName;
	private String targetShopName;
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getPadNo() {
		return padNo;
	}
	public void setPadNo(String padNo) {
		this.padNo = padNo;
	}
	public Date getOperatTime() {
		return operatTime;
	}
	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTargetShopName() {
		return targetShopName;
	}
	public void setTargetShopName(String targetShopName) {
		this.targetShopName = targetShopName;
	}

}
