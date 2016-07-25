package net.shopin.supply.persistence;

import java.util.List;
import java.util.Map;
import net.shopin.supply.domain.entity.SystemUser;
import com.shopin.core.framework.base.persistence.BaseMapper;

public interface SystemUserMapper extends BaseMapper<SystemUser>{
	
	public SystemUser getSystemUserByParam(Map<String, Object> paramMap);
	
	public Integer updateUserinfoValidStatus(SystemUser systemUser);
	
	public SystemUser selectByUserCode(SystemUser user)throws Exception;
	
	public List<SystemUser> getAllValidUser();
	
	public Integer getCountByPrimaryKey(Map<String, Object> paramMap);
	
	public List<SystemUser> selectByPrimaryKeyPage(Map<String, Object> paramMap);
	
	public Integer delete(int sid);
	
	public SystemUser checkIsUnique(Map<String ,Object> map);
}
