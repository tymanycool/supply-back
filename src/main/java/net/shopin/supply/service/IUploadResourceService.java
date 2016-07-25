/**   
* @Title: IResourcesService.java 
* @Package net.shopin.supply.service 
* @Description: TODO(...) 
* @author zhangq   
* @date 2015-3-2 上午10:46:06 
* @version V1.0   
*/
package net.shopin.supply.service;

import net.shopin.supply.domain.entity.UploadResource;

/**
 * 
 * ClassName: IUploadResourceService 
 * @Description: TODO
 * @author zhangqing
 * @date 2015-4-21
 */
public interface IUploadResourceService {
	
	public UploadResource getResourcesByParam(int shopSid);
}
