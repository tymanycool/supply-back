package net.shopin.supply.domain.vo;

import java.util.Date;

/**
 * 导购信息vo
 * @author zhangq
 *
 */
public class GuideinfoVO {
	
	private Integer sid;//AUTO_INCREMENT
    private Integer guideNo;//导购 编号
    private String name;//姓名
    private String spell;//姓名拼音
    private String mobile;//手机号码
    private String email;//邮箱
    private String guideCard;//身份证号码
    private String address;//家庭住址
    private String presentAddress;//现住址
    private String educationCartNum;//学历证编号
    private String kitasNum;//暂住证编号
    private Date kitasEndtime;//暂住证有效时间至
    private String healthCartNum;//健康证编号
    private Date healthCartEndtime;//健康证有效时间至
    private String sex;//性别
    private Integer age;//年龄
    private String stature;//身高
    private String education;//学历
    private Integer guideBit;//是否是导购 0:不是 1:是
    private Integer validBit;//是否有效 0:无效 1:有效
    private String operator;//操作人姓名
    private Date operatorTime;//操作时间
    private Date createtime;//创建时间
    private Integer chestBit;//是否领取胸卡 0：未领取；1：临时胸卡；2：正式胸卡
    private Integer depositBit;//是否已交押金 0：未交；1：已交
    private Integer guideStatus;//导购状态 0：初始状态；1：在柜；2不在柜
    private String depositNum;//押金单据编号
    private Date entrytime;//入店时间
    private Date leavetime;//离店时间
    private String chestcardNumber;//胸卡编号
    private Integer supplyId;//供应商id
    private String supplyName;//供应商名称
    private String brand;//品牌
    private String categroys;//品类
    private Integer shopId;//门店
    private String shopName;//门店名称
    
	 private Integer changeSupplyId;//转场后供应商id
	 private String changeSupplyName;//转场后供应商名称
	 private String changeBrandId;//转场后品牌id
	 private String changeBrand;//转场后品牌名称
	 private Integer changeCategorysId;//转场后品类id
	 private String changeCategroys;//转场后品类名称
	 private Integer changeSupplyBit;//是否转场 0:不是 1:是
	 
	 //手动变价权限
     private Date startTime;
     private Date endTime;
     private String operatorId;
     private String operatoeName;
     private Date operatTime;
     private Integer flag;
	 
     
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatoeName() {
		return operatoeName;
	}
	public void setOperatoeName(String operatoeName) {
		this.operatoeName = operatoeName;
	}
	public Date getOperatTime() {
		return operatTime;
	}
	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Integer getGuideNo() {
		return guideNo;
	}
	public void setGuideNo(Integer guideNo) {
		this.guideNo = guideNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpell() {
		return spell;
	}
	public void setSpell(String spell) {
		this.spell = spell;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGuideCard() {
		return guideCard;
	}
	public void setGuideCard(String guideCard) {
		this.guideCard = guideCard;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getStature() {
		return stature;
	}
	public void setStature(String stature) {
		this.stature = stature;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public Integer getGuideBit() {
		return guideBit;
	}
	public void setGuideBit(Integer guideBit) {
		this.guideBit = guideBit;
	}
	public Integer getValidBit() {
		return validBit;
	}
	public void setValidBit(Integer validBit) {
		this.validBit = validBit;
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
	public String getChestcardNumber() {
		return chestcardNumber;
	}
	public void setChestcardNumber(String chestcardNumber) {
		this.chestcardNumber = chestcardNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPresentAddress() {
		return presentAddress;
	}
	public void setPresentAddress(String presentAddress) {
		this.presentAddress = presentAddress;
	}
	public String getEducationCartNum() {
		return educationCartNum;
	}
	public void setEducationCartNum(String educationCartNum) {
		this.educationCartNum = educationCartNum;
	}
	public String getKitasNum() {
		return kitasNum;
	}
	public void setKitasNum(String kitasNum) {
		this.kitasNum = kitasNum;
	}
	public Date getKitasEndtime() {
		return kitasEndtime;
	}
	public void setKitasEndtime(Date kitasEndtime) {
		this.kitasEndtime = kitasEndtime;
	}
	public String getHealthCartNum() {
		return healthCartNum;
	}
	public void setHealthCartNum(String healthCartNum) {
		this.healthCartNum = healthCartNum;
	}
	public Date getHealthCartEndtime() {
		return healthCartEndtime;
	}
	public void setHealthCartEndtime(Date healthCartEndtime) {
		this.healthCartEndtime = healthCartEndtime;
	}
	public Date getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	public Integer getChestBit() {
		return chestBit;
	}
	public void setChestBit(Integer chestBit) {
		this.chestBit = chestBit;
	}
	public Integer getDepositBit() {
		return depositBit;
	}
	public void setDepositBit(Integer depositBit) {
		this.depositBit = depositBit;
	}
	public Integer getGuideStatus() {
		return guideStatus;
	}
	public void setGuideStatus(Integer guideStatus) {
		this.guideStatus = guideStatus;
	}
	public String getDepositNum() {
		return depositNum;
	}
	public void setDepositNum(String depositNum) {
		this.depositNum = depositNum;
	}
	public Date getEntrytime() {
		return entrytime;
	}
	public void setEntrytime(Date entrytime) {
		this.entrytime = entrytime;
	}
	public Date getLeavetime() {
		return leavetime;
	}
	public void setLeavetime(Date leavetime) {
		this.leavetime = leavetime;
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
	public String getCategroys() {
		return categroys;
	}
	public void setCategroys(String categroys) {
		this.categroys = categroys;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getChangeSupplyId() {
		return changeSupplyId;
	}
	public void setChangeSupplyId(Integer changeSupplyId) {
		this.changeSupplyId = changeSupplyId;
	}
	public String getChangeSupplyName() {
		return changeSupplyName;
	}
	public void setChangeSupplyName(String changeSupplyName) {
		this.changeSupplyName = changeSupplyName;
	}
	public String getChangeBrandId() {
		return changeBrandId;
	}
	public void setChangeBrandId(String changeBrandId) {
		this.changeBrandId = changeBrandId;
	}
	public String getChangeBrand() {
		return changeBrand;
	}
	public void setChangeBrand(String changeBrand) {
		this.changeBrand = changeBrand;
	}
	public Integer getChangeCategorysId() {
		return changeCategorysId;
	}
	public void setChangeCategorysId(Integer changeCategorysId) {
		this.changeCategorysId = changeCategorysId;
	}
	public String getChangeCategroys() {
		return changeCategroys;
	}
	public void setChangeCategroys(String changeCategroys) {
		this.changeCategroys = changeCategroys;
	}
	public Integer getChangeSupplyBit() {
		return changeSupplyBit;
	}
	public void setChangeSupplyBit(Integer changeSupplyBit) {
		this.changeSupplyBit = changeSupplyBit;
	}
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
