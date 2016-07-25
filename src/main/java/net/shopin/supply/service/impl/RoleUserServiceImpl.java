package net.shopin.supply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.supply.domain.entity.RoleUser;
import net.shopin.supply.persistence.RoleUserMapper;
import net.shopin.supply.service.IRoleUserService;

/** 
 * @ClassName: RoleUserServiceImpl 
 * @author zhangq
 * @date 2015-3-2 下午01:52:33 
 *  
 */
@Service
public class RoleUserServiceImpl implements IRoleUserService {

	@Autowired
	private RoleUserMapper roleUserMapper;
	
	@Override
	public Integer deleteRoleUser(Long sid) throws Exception {
		return roleUserMapper.delete(sid);
	}
	
	@Override
	public List<RoleUser> getRoleUserByParam(RoleUser roleUser)
			throws Exception {
		return roleUserMapper.selectByPrimaryKey(roleUser);
	}

	@Override
	public Integer saveRoleUser(RoleUser roleUser) throws Exception {
		return roleUserMapper.saveRoleUser(roleUser);
	}


}
