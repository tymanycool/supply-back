package net.shopin.supply.service;

import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.SystemUser;

import com.shopin.core.framework.base.vo.Page;

public interface ISystemUserService {
	
	/**
	 * 获取用户信息列表
	* @Title: getAllSystemUser 
	* @Description: TODO(....) 
	* @param @param map
	* @param @return    设定文件 
	* @return List<SystemUser>    返回类型 
	* @throws
	 */
	public List<SystemUser> getAllSystemUser(Map<String,Object> map);
	
	/**
	 * 获取数据总条数（用于分页）
	* @Title: getCountByParam 
	* @Description: TODO(....) 
	* @param @param map
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int getCountByParam(Map<String,Object> map);
	
	/**
	 * 保存用户信息
	* @Title: saveSystemUser 
	* @param @param systemUser
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public Integer saveSystemUser(SystemUser systemUser);
	
	/**
	 * 修改
	* @Title: update 
	* @param @param systemUser
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer update(SystemUser systemUser);
	
	/**
	 * 获取所有有效用户
	* @Title: getAllValidUser 
	* @param @return    设定文件 
	* @return List<SystemUser>    返回类型 
	* @throws
	 */
	public List<SystemUser> getAllValidUser();
	
	public SystemUser getSystemUserByParam(Map<String,Object> map);
	
	public Integer updateUserinfoValidStatus(SystemUser systemUser);
	
	public SystemUser selectByUserCode(SystemUser user)throws Exception;
	
	public Page<SystemUser> selectAllbyParam(Map<String, Object> paramMap)throws Exception;
	
	public Integer delGuidinfo(int sid);
	
	/**
	 * 检查是否唯一
	 * @Title: checkIsUnique 
	 * @Description: TODO
	 * @param @param map
	 * @param @return   
	 * @return SystemUser  
	 * @throws
	 * @author zhangqing
	 * @date 2015-4-29
	 */
	public SystemUser checkIsUnique(Map<String ,Object> map);
	
}
