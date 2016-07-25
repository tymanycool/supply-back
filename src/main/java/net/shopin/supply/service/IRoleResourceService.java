/**   
* @Title: IRoleResourceService.java 
* @Package net.shopin.supply.service 
* @Description: TODO(...) 
* @author zhangq   
* @date 2015-3-2 下午02:03:47 
* @version V1.0   
*/
package net.shopin.supply.service;

import java.util.List;

import net.shopin.supply.domain.entity.RoleResources;

/** 
 * @ClassName: IRoleResourceService 
 * @author zhangq
 * @date 2015-3-2 下午02:03:47 
 *  
 */
public interface IRoleResourceService {
	
	public Integer deleteRoleResources(RoleResources roleresource)throws Exception;
	
	public List<RoleResources> getRoleResourcesByParam(RoleResources roleresource)throws Exception;
	
	public Integer saveRoleResources(RoleResources roleresources)throws Exception;

}
