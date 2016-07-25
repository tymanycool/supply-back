package net.shopin.supply.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.shopin.supply.domain.entity.UploadResource;
import net.shopin.supply.persistence.UploadResourceMapper;
import net.shopin.supply.service.IUploadResourceService;

@Service
public class UploadResourceServiceImpl implements IUploadResourceService {

	@Autowired
	private UploadResourceMapper uploadResourceMapper;
	
	@Override
	public UploadResource getResourcesByParam(int shopSid) {
		return this.uploadResourceMapper.getResourcesByParam(shopSid);
	}


}
