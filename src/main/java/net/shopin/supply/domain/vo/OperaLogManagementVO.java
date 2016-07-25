/**
* 导购手动变价权限日志报表
* for demand
* feature https://tower.im/s/beCeH
* author qutengfei
*/
package net.shopin.supply.domain.vo;

import java.util.Date;

/**
 * 操作日志管理vo
 * @author qutengfei
 *
 */
public class OperaLogManagementVO {
	private Integer sid;//AUTO_INCREMENT
	private Integer guideNo;//导购 编号
    private Date operatTime;//操作时间
    private String operator;//姓名
    private String description;//操作描述
    private Integer type;//操作类型(1.修改导购变价权限2修改基本信息)'
    private String typeDesc;//操作类型描述
    private String operatorId;//操作人ID
    
    private String endTime;//结束时间
    private String startTime;//开始时间
    private String ifopen;//'权限是否开通 0：否；1：是'
    
    
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getIfopen() {
		return ifopen;
	}
	public void setIfopen(String ifopen) {
		this.ifopen = ifopen;
	}
    
    
}
