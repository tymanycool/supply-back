package net.shopin.supply.persistence;

import java.util.List;

import net.shopin.supply.domain.entity.Resources;
import net.shopin.supply.domain.entity.UploadResource;

import com.shopin.core.framework.base.persistence.BaseMapper;

/**
 * 
 * ClassName: UploadResourceMapper 
 * @Description: TODO
 * @author zhangqing
 * @date 2015-4-21
 */
public interface UploadResourceMapper extends BaseMapper<UploadResource>{
	
	public UploadResource getResourcesByParam(int shopSid);
	

}
