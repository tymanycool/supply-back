package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * 供应商信息表
* @ClassName: SupplyInfo 
* @author zhangq
* @date 2014-12-29 上午11:57:18 
*
 */
public class SupplyInfo {
	
	    private Integer sid;//AUTO_INCREMENT
	    private Integer supplyId;
	    private String supplyName;//供应商名称
	    private String supplyAdderss;//供应商地址
	    private String country;//国家
	    private String timezone;//时区
	    private String taxJurisdiction;//税务管辖权
	    private String language;//语言
	    private String mobile;//电话
	    private String taxCode;//地区税务代码
	    private String industry;//行业
	    private String controllAccount;//统驭科目
	    private Date planDeliveryTime;//计划交付时间
	    private String operator;//'操作人
	    private Date createtime;//创建时间
	    private Date auditTime;//审核时间
	    private Integer SAPStatus;//SAP状态
		public Integer getSid() {
			return sid;
		}
		public void setSid(Integer sid) {
			this.sid = sid;
		}
		public Integer getSupplyId() {
			return supplyId;
		}
		public void setSupplyId(Integer supplyId) {
			this.supplyId = supplyId;
		}
		public String getSupplyName() {
			return supplyName;
		}
		public void setSupplyName(String supplyName) {
			this.supplyName = supplyName;
		}
		public String getSupplyAdderss() {
			return supplyAdderss;
		}
		public void setSupplyAdderss(String supplyAdderss) {
			this.supplyAdderss = supplyAdderss;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getTimezone() {
			return timezone;
		}
		public void setTimezone(String timezone) {
			this.timezone = timezone;
		}
		public String getTaxJurisdiction() {
			return taxJurisdiction;
		}
		public void setTaxJurisdiction(String taxJurisdiction) {
			this.taxJurisdiction = taxJurisdiction;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getTaxCode() {
			return taxCode;
		}
		public void setTaxCode(String taxCode) {
			this.taxCode = taxCode;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getControllAccount() {
			return controllAccount;
		}
		public void setControllAccount(String controllAccount) {
			this.controllAccount = controllAccount;
		}
		public Date getPlanDeliveryTime() {
			return planDeliveryTime;
		}
		public void setPlanDeliveryTime(Date planDeliveryTime) {
			this.planDeliveryTime = planDeliveryTime;
		}
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public Date getCreatetime() {
			return createtime;
		}
		public void setCreatetime(Date createtime) {
			this.createtime = createtime;
		}
		public Date getAuditTime() {
			return auditTime;
		}
		public void setAuditTime(Date auditTime) {
			this.auditTime = auditTime;
		}
		public Integer getSAPStatus() {
			return SAPStatus;
		}
		public void setSAPStatus(Integer sAPStatus) {
			SAPStatus = sAPStatus;
		}
}
