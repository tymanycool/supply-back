/**   
* @Title: IResourcesService.java 
* @Package net.shopin.supply.service 
* @Description: TODO(...) 
* @author zhangq   
* @date 2015-3-2 上午10:46:06 
* @version V1.0   
*/
package net.shopin.supply.service;

import java.util.List;

import net.shopin.supply.domain.entity.Resources;

/** 
 * 资源接口
 * @ClassName: IResourcesService 
 * @author zhangq
 * @date 2015-3-2 上午10:46:06 
 *  
 */
public interface IResourcesService {
	
	/**
	 * 根据条件查询资源信息
	* @Title: getResourcesByParam 
	* @param @param resource
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<Resources>    返回类型 
	* @throws
	 */
	public List<Resources> getResourcesByParam(Resources resource)throws Exception;
	
	/**
	 * 获取所有资源
	* @Title: getAll 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<Resources>    返回类型 
	* @throws
	 */
	public List<Resources> getAllResources()throws Exception;
	
	/**
	 * 添加资源
	* @Title: saveResource 
	* @param @param resource
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer saveResource(Resources resource)throws Exception;
	
	public Integer updateResource(Resources resource)throws Exception;
	
	/**
	 * 删除资源
	* @Title: deleteResource 
	* @param @param resource
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer deleteResource(long sid)throws Exception;
}
