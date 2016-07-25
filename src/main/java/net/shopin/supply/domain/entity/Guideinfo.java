package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * 导购信息表
 * @author zhangq
 *
 */
public class Guideinfo {
	
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
//	    private Integer receivePadBit;//是否领用pad 0:未领用 1：已领用
	    private String operator;//操作人姓名
//	    private Integer operatorSid;//操作人id
	    private Date operatorTime;//操作时间
	    private Date createtime;//创建时间
	    private Integer chestBit;//是否领取胸卡 0：未领取；1：临时胸卡；2：正式胸卡
	    private Integer depositBit;//是否已交押金 0：未交；1：已交
	    private Integer guideStatus;//导购状态 0：初始状态；1：在柜；2不在柜
	    private String depositNum;//押金单据编号
	    private Date entrytime;//入店时间
	    private Date leavetime;//离店时间
	    private Integer authorize;//是否有手动添加补单的权限（1 有 0 否）
	    private Date endtime;//权限截止时间
	    
	    
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
		public String getSpell() {
			return spell;
		}
		public void setSpell(String spell) {
			this.spell = spell;
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
		public Date getOperatorTime() {
			return operatorTime;
		}
		public void setOperatorTime(Date operatorTime) {
			this.operatorTime = operatorTime;
		}
		public Date getCreatetime() {
			return createtime;
		}
		public void setCreatetime(Date createtime) {
			this.createtime = createtime;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
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
		public String getHealthCartNum() {
			return healthCartNum;
		}
		public void setHealthCartNum(String healthCartNum) {
			this.healthCartNum = healthCartNum;
		}
		public Integer getGuideBit() {
			return guideBit;
		}
		public void setGuideBit(Integer guideBit) {
			this.guideBit = guideBit;
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
		public String getPresentAddress() {
			return presentAddress;
		}
		public void setPresentAddress(String presentAddress) {
			this.presentAddress = presentAddress;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public Date getKitasEndtime() {
			return kitasEndtime;
		}
		public void setKitasEndtime(Date kitasEndtime) {
			this.kitasEndtime = kitasEndtime;
		}
		public Date getHealthCartEndtime() {
			return healthCartEndtime;
		}
		public void setHealthCartEndtime(Date healthCartEndtime) {
			this.healthCartEndtime = healthCartEndtime;
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
		public Integer getGuideStatus() {
			return guideStatus;
		}
		public void setGuideStatus(Integer guideStatus) {
			this.guideStatus = guideStatus;
		}
		public String getEducation() {
			return education;
		}
		public void setEducation(String education) {
			this.education = education;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
//		public Integer getOperatorSid() {
//			return operatorSid;
//		}
//		public void setOperatorSid(Integer operatorSid) {
//			this.operatorSid = operatorSid;
//		}
		public Integer getAuthorize() {
			return authorize;
		}
		public void setAuthorize(Integer authorize) {
			this.authorize = authorize;
		}
		public Date getEndtime() {
			return endtime;
		}
		public void setEndtime(Date endtime) {
			this.endtime = endtime;
		}
}
