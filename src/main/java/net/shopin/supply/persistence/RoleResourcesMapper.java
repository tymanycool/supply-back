package net.shopin.supply.persistence;

import java.util.List;
import net.shopin.supply.domain.entity.RoleResources;
import com.shopin.core.framework.base.persistence.BaseMapper;

/**
 * 
* @ClassName: RoleResourcesMapper 
* @author zhangq
* @date 2015-3-2 下午02:00:50 
*
 */
public interface RoleResourcesMapper extends BaseMapper<RoleResources>{
	
	public List<RoleResources> getRoleResourcesByParam(RoleResources roleresource)throws Exception;
	
	public Integer deleteResources(RoleResources roleresource);
	
}
