/**
 * 
 */
package net.shopin.supply.domain.entity;

/**
 * 角色资源关系表
* @ClassName: RoleResources 
* @author zhangq
* @date 2015-3-2 下午02:01:43 
*
 */
public class RoleResources {

	private Long sid;
	private Long roleSid;// 角色Sid
	private Long rsSid;// 资源Sid
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public Long getRoleSid() {
		return roleSid;
	}
	public void setRoleSid(Long roleSid) {
		this.roleSid = roleSid;
	}
	public Long getRsSid() {
		return rsSid;
	}
	public void setRsSid(Long rsSid) {
		this.rsSid = rsSid;
	}
}
