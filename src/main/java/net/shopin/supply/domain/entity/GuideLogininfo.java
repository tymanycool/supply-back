package net.shopin.supply.domain.entity;

import java.util.Date;

public class GuideLogininfo {
	
	 private Integer sid;
	 private Integer guideNo;//导购编号
	 private Integer shopId;//门店ID
	 private String shopName;//门店名称
	 private String chestcardShoprule;//胸卡门店规则
	 private Integer chestcardNum;//编号(胸卡编号=chestcard_shoprule+chestcard_num)
	 private String chestcardNumber;//胸卡编号
	 private String realName;//真实姓名
	 private String loginUsername;//用户名
	 private String loginPassword;//密码
	 private Integer validBit;//是否有效 0:无效 1:有效
	 private Date creattime;//创建时间
	 
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
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getChestcardShoprule() {
		return chestcardShoprule;
	}
	public void setChestcardShoprule(String chestcardShoprule) {
		this.chestcardShoprule = chestcardShoprule;
	}
	public Date getCreattime() {
		return creattime;
	}
	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
	public Integer getChestcardNum() {
		return chestcardNum;
	}
	public void setChestcardNum(Integer chestcardNum) {
		this.chestcardNum = chestcardNum;
	}
	public Integer getValidBit() {
		return validBit;
	}
	public void setValidBit(Integer validBit) {
		this.validBit = validBit;
	}
	public String getChestcardNumber() {
		return chestcardNumber;
	}
	public void setChestcardNumber(String chestcardNumber) {
		this.chestcardNumber = chestcardNumber;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getLoginUsername() {
		return loginUsername;
	}
	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
