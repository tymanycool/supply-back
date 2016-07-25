package net.shopin.supply.persistence;

import java.util.List;
import net.shopin.supply.domain.entity.RoleUser;
import com.shopin.core.framework.base.persistence.BaseMapper;

/**
 * 
* @ClassName: RoleUserMapper 
* @author zhangq
* @date 2015-3-2 下午01:50:11 
*
 */
public interface RoleUserMapper extends BaseMapper<RoleUser>{
	
	List<RoleUser> selectByPrimaryKey(RoleUser roleUser);
	
	public Integer saveRoleUser(RoleUser roleUser)throws Exception;
	
}