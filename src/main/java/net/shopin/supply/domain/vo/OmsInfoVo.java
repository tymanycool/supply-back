package net.shopin.supply.domain.vo;

import java.sql.Date;

public class OmsInfoVo {
	
	private Long shopSid;
	private String shopName;
	private Date payTime;
	private Long supplySid;
	private String brandName;
	private String brandSid;
	private Long paymentTypeSid;
	private String cashierNumber; //流水号
	private Integer saleSum;
	private String deviceEn; //设备en号
	private String bankNo; //银行卡号
	private String refNo; //参考号
	private String terminalNo;//银行终端号
	private String cashierNo;//收银员号
	private String startTime;//开始时间
	private String endTime;
	private String saleAllPrice;//总金额
	private String guideNo; //导购登陆号
	private Long count;//总条数
	
	
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getGuideNo() {
		return guideNo;
	}
	public void setGuideNo(String guideNo) {
		this.guideNo = guideNo;
	}
	public String getSaleAllPrice() {
		return saleAllPrice;
	}
	public void setSaleAllPrice(String saleAllPrice) {
		this.saleAllPrice = saleAllPrice;
	}
	public Long getShopSid() {
		return shopSid;
	}
	public void setShopSid(Long shopSid) {
		this.shopSid = shopSid;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Long getSupplySid() {
		return supplySid;
	}
	public void setSupplySid(Long supplySid) {
		this.supplySid = supplySid;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandSid() {
		return brandSid;
	}
	public void setBrandSid(String brandSid) {
		this.brandSid = brandSid;
	}
	public Long getPaymentTypeSid() {
		return paymentTypeSid;
	}
	public void setPaymentTypeSid(Long paymentTypeSid) {
		this.paymentTypeSid = paymentTypeSid;
	}
	public String getCashierNumber() {
		return cashierNumber;
	}
	public void setCashierNumber(String cashierNumber) {
		this.cashierNumber = cashierNumber;
	}
	public Integer getSaleSum() {
		return saleSum;
	}
	public void setSaleSum(Integer saleSum) {
		this.saleSum = saleSum;
	}
	public String getDeviceEn() {
		return deviceEn;
	}
	public void setDeviceEn(String deviceEn) {
		this.deviceEn = deviceEn;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getCashierNo() {
		return cashierNo;
	}
	public void setCashierNo(String cashierNo) {
		this.cashierNo = cashierNo;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
	
	

	
	
	

}
