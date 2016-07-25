package net.shopin.supply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.supply.domain.entity.RoleResources;
import net.shopin.supply.persistence.RoleResourcesMapper;
import net.shopin.supply.service.IRoleResourceService;

/** 
 * @ClassName: RoleResourceServiceImpl 
 * @author zhangq
 * @date 2015-3-2 下午02:04:12 
 *  
 */
@Service
public class RoleResourceServiceImpl implements IRoleResourceService {

	@Autowired
	private RoleResourcesMapper roleResourcesMapper;
	
	@Override
	public Integer deleteRoleResources(RoleResources roleresource)
			throws Exception {
		return roleResourcesMapper.deleteResources(roleresource);
	}

	@Override
	public List<RoleResources> getRoleResourcesByParam(RoleResources roleresource)
			throws Exception {
		return roleResourcesMapper.getRoleResourcesByParam(roleresource);
	}

	@Override
	public Integer saveRoleResources(RoleResources roleresources)
			throws Exception {
		return roleResourcesMapper.insert(roleresources);
	}

}
