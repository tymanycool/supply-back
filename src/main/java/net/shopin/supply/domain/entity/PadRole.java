package net.shopin.supply.domain.entity;

/**
 * PAD用户角色表
 * @author admin
 *
 */
public class PadRole {
	
	private Integer sid;
	private Integer roleNo;//角色编号
	private String roleName;//角色名称
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(Integer roleNo) {
		this.roleNo = roleNo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	

}
