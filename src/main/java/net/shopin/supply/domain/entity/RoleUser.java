package net.shopin.supply.domain.entity;
/**
 * 
* @ClassName: RoleUser 
* @author zhangq
* @date 2015-3-2 下午01:57:04 
*
 */
public class RoleUser {
    private Long sid;

    private Long userSid;

    private Long roleSid;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getUserSid() {
        return userSid;
    }

    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    public Long getRoleSid() {
        return roleSid;
    }

    public void setRoleSid(Long roleSid) {
        this.roleSid = roleSid;
    }
}