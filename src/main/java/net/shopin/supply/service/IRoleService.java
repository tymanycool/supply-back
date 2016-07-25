package net.shopin.supply.service;

import java.util.List;
import net.shopin.supply.domain.entity.Role;

/** 
 * @ClassName: IRoleService 
 * @author zhangq
 * @date 2015-3-2 下午01:24:17 
 *  
 */
public interface IRoleService {
	
	public List getRoleByParam(Role role)throws Exception;
	
	public List getAllRole()throws Exception;
	
	public Integer updateRole(Role role)throws Exception;
	
	public Integer saveRole(Role role)throws Exception;

}
