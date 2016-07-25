package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * 导购日志表
 * @author zhangq
 *
 */
public class GuideLog {
	
	private Integer sid;
	private Integer guideNo;//导购编号
	private Date operatTime;//操作时间
	private String operator;//操作人姓名
	private String description;//操作描述
	private Integer type;//类型
	private String typeDesc;//类型描述
	private String operatorId;//操作人id
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getGuideNo() {
		return guideNo;
	}
	public void setGuideNo(Integer guideNo) {
		this.guideNo = guideNo;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
}
