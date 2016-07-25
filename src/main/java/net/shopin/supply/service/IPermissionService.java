package net.shopin.supply.service;

import java.util.Map;
import net.shopin.supply.domain.entity.Permission;

public interface IPermissionService {
	public Integer save(Permission permission);
	public Permission selectByParam(Map<String,Object> map);
}
