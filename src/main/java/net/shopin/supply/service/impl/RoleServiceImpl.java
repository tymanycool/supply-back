/**   
* @Title: RoleServiceImpl.java 
* @Package net.shopin.supply.service.impl 
* @Description: TODO(...) 
* @author zhangq   
* @date 2015-3-2 下午01:25:31 
* @version V1.0   
*/
package net.shopin.supply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.supply.domain.entity.Role;
import net.shopin.supply.persistence.RoleMapper;
import net.shopin.supply.service.IRoleService;

/** 
 * @ClassName: RoleServiceImpl 
 * @Description: TODO(...) 
 * @author zhangq
 * @date 2015-3-2 下午01:25:31 
 *  
 */
@Service
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public List getRoleByParam(Role role) throws Exception {
		return roleMapper.getRoleByParam(role);
	}

	@Override
	public List getAllRole() throws Exception {
		List role = roleMapper.getAllRole();
		return role;
	}

	@Override
	public Integer updateRole(Role role) throws Exception {
		return roleMapper.update(role);
	}

	@Override
	public Integer saveRole(Role role) throws Exception {
		return roleMapper.insert(role);
	}

}
