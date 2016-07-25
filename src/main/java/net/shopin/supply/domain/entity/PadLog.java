package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * PAD日志表
 * @author zhangq
 *
 */
public class PadLog {
	
	private Integer sid;
	private String padNo;//pad编号
	private Date operatTime;//操作时间
	private String operator;//操作人姓名
	private Integer operatorSid; //操作人Sid
	private String description;//操作描述
	
	public Integer getOperatorSid() {
		return operatorSid;
	}
	public void setOperatorSid(Integer operatorSid) {
		this.operatorSid = operatorSid;
	}
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Date getOperatTime() {
		return operatTime;
	}
	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
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
	public String getPadNo() {
		return padNo;
	}
	public void setPadNo(String padNo) {
		this.padNo = padNo;
	}
	@Override
	public String toString() {
		return "PadLog [sid=" + sid + ", padNo=" + padNo + ", operatTime=" + operatTime + ", operator=" + operator
				+ ", operatorSid=" + operatorSid + ", description=" + description + "]";
	}
	
}
