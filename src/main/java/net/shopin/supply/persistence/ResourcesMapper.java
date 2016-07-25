package net.shopin.supply.persistence;

import java.util.List;

import com.shopin.core.framework.base.persistence.BaseMapper;

import net.shopin.supply.domain.entity.Resources;

/**
 * 
* @ClassName: ResourceMapper 
* @author zhangq
* @date 2015-3-2 上午11:24:23 
*
 */
public interface ResourcesMapper extends BaseMapper<Resources>{
	
	/**
	 * 根据条件查询资源
	* @Title: getResourcesByParam 
	* @Description: TODO(....) 
	* @param @param resource
	* @param @return    设定文件 
	* @return List<Resources>    返回类型 
	* @throws
	 */
	public List<Resources> getResourcesByParam(Resources resource);
	
	/**
	 * 获取所有资源
	* @Title: getAllResource 
	* @param @return    设定文件 
	* @return List<Resources>    返回类型 
	* @throws
	 */
	public List<Resources> getAllResource();

}
