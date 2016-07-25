package net.shopin.supply.domain.entity;

import java.util.Date;

/**
 * 角色
* @ClassName: Role 
* @author zhangq
* @date 2015-3-2 下午01:22:22 
*
 */
public class Role {
	
	private Long sid;
	private String roleName;// 角色名称
	private String roleCode;// 角色编码
	private Date createdTime;// 创建时间
	private Date updateTime;// 修改时间
	private Integer delFlag;// 删除标识符,1未删除0删除
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}