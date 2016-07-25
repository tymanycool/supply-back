package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * 系统用户信息表
 * @author zhangq
 *
 */
public class SystemUser {
	
	    private Integer sid;//AUTO_INCREMENT
	    private String userCode;//用户姓名拼音
	    private String username;
	    private String userPssword;
	    private String sex;
	    private String mobile;//手机号码
	    private Integer shopSid;
	    private String shopName;
	    private Integer validBit;//是否有效 0:无效 1:有效
	    private String operator;//操作人姓名
	    private Date createtime;//创建时间
		public Integer getSid() {
			return sid;
		}
		public void setSid(Integer sid) {
			this.sid = sid;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getUserPssword() {
			return userPssword;
		}
		public void setUserPssword(String userPssword) {
			this.userPssword = userPssword;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public Integer getShopSid() {
			return shopSid;
		}
		public void setShopSid(Integer shopSid) {
			this.shopSid = shopSid;
		}
		public String getShopName() {
			return shopName;
		}
		public void setShopName(String shopName) {
			this.shopName = shopName;
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
		public String getUserCode() {
			return userCode;
		}
		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}
}
