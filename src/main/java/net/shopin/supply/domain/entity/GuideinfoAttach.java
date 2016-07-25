package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * 导购附属信息表(家庭成员、工作经验等信息)
 * @author zhangq
 *
 */
public class GuideinfoAttach {
	
	    private Integer sid;//AUTO_INCREMENT
	    private Integer guideNo;//导购编号
	    private Integer type;//类型1：家庭成员信息；2：工作经历信息
	    private String familyName;//家庭成员姓名
	    private String relation;//家庭成员关系
	    private String mobile;//家庭成员联系电话
	    private Date workStarttime;//工作开始时间
	    private Date workEndtime;//工作截止时间
	    private String company;//工作单位(或品牌名称)
	    private String position;//职务
	    private String leaveResult;//离职原因
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
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getRelation() {
			return relation;
		}
		public void setRelation(String relation) {
			this.relation = relation;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		
		public Date getWorkStarttime() {
			return workStarttime;
		}
		public void setWorkStarttime(Date workStarttime) {
			this.workStarttime = workStarttime;
		}
		public Date getWorkEndtime() {
			return workEndtime;
		}
		public void setWorkEndtime(Date workEndtime) {
			this.workEndtime = workEndtime;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getPosition() {
			return position;
		}
		public void setPosition(String position) {
			this.position = position;
		}
		public String getLeaveResult() {
			return leaveResult;
		}
		public void setLeaveResult(String leaveResult) {
			this.leaveResult = leaveResult;
		}
		public String getFamilyName() {
			return familyName;
		}
		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}
		public Date getCreattime() {
			return creattime;
		}
		public void setCreattime(Date creattime) {
			this.creattime = creattime;
		}
}
