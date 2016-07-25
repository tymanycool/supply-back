package net.shopin.supply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.supply.domain.entity.Resources;
import net.shopin.supply.persistence.ResourcesMapper;
import net.shopin.supply.service.IResourcesService;

/** 
 * @ClassName: ResourcesServiceImpl 
 * @author zhangq
 * @date 2015-3-2 上午10:47:11 
 *  
 */
@Service
public class ResourcesServiceImpl implements IResourcesService {

	@Autowired
	private ResourcesMapper resourceMapper;
	
	/**
	 * 根据条件查询资源信息
	 */
	@Override
	public List<Resources> getResourcesByParam(Resources resource) throws Exception {
		return resourceMapper.getResourcesByParam(resource);
	}

	/**
	 * 获取所有资源
	 */
	@Override
	public List<Resources> getAllResources() throws Exception {
		return resourceMapper.getAllResource();
	}

	@Override
	public Integer saveResource(Resources resource) throws Exception {
		return resourceMapper.insert(resource);
	}

	@Override
	public Integer updateResource(Resources resource) throws Exception {
		return resourceMapper.update(resource);
	}

	@Override
	public Integer deleteResource(long sid) throws Exception {
		return resourceMapper.delete(sid);
	}

	

}
