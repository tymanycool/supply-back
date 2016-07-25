/**   
* @Title: IRoleUserService.java 
* @Package net.shopin.supply.service 
* @Description: TODO(...) 
* @author zhangq   
* @date 2015-3-2 下午01:52:10 
* @version V1.0   
*/
package net.shopin.supply.service;

import java.util.List;
import net.shopin.supply.domain.entity.RoleUser;

/** 
 * @ClassName: IRoleUserService 
 * @Description: TODO(...) 
 * @author zhangq
 * @date 2015-3-2 下午01:52:10 
 *  
 */
public interface IRoleUserService {
	
	public Integer deleteRoleUser(Long sid)throws Exception;
	
	public List<RoleUser> getRoleUserByParam(RoleUser roleUser)throws Exception;
	
	public Integer saveRoleUser(RoleUser roleUser)throws Exception;

}
