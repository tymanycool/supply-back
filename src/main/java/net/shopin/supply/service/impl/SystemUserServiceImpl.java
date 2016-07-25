package net.shopin.supply.service.impl;

import java.util.List;
import java.util.Map;
import net.shopin.supply.domain.entity.SystemUser;
import net.shopin.supply.persistence.SystemUserMapper;
import net.shopin.supply.service.ISystemUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopin.core.framework.base.vo.Page;

@Service
public class SystemUserServiceImpl implements ISystemUserService {

	private Logger logger = Logger.getLogger(SystemUserServiceImpl.class);
	
	@Autowired
	private SystemUserMapper systemUserMapper;

	/**
	 * 获取用户信息列表
	 */
	@Override
	public List<SystemUser> getAllSystemUser(Map<String,Object> map) {
		
		List<SystemUser> list = this.systemUserMapper.selectPageListByParam(map);
		return list;
	}

	/**
	 * 获取总条数（用于分页）
	 */
	@Override
	public int getCountByParam(Map<String, Object> map) {
		int count = this.systemUserMapper.getCountByParam(map);
		return count;
	}

	/**
	 * 保存用户信息
	 */
	@Override
	public Integer saveSystemUser(SystemUser systemUser) {
		
		int result = 0;
		if(systemUser.getSid() == null){
			result = this.systemUserMapper.insert(systemUser);
		}else{
			result = this.systemUserMapper.update(systemUser);
		}
		return result;
	}

	@Override
	public SystemUser getSystemUserByParam(Map<String, Object> map) {
		SystemUser systemUser = this.systemUserMapper.getSystemUserByParam(map);
		return systemUser;
	}

	/**
	 * 删除用户信息
	 */
	@Override
	public Integer updateUserinfoValidStatus(SystemUser systemUser) {
		int result = this.systemUserMapper.updateUserinfoValidStatus(systemUser);
		return result;
	}

	@Override
	public SystemUser selectByUserCode(SystemUser user) throws Exception {
		return systemUserMapper.selectByUserCode(user);
	}

	@Override
	public List<SystemUser> getAllValidUser() {
		List<SystemUser> list = systemUserMapper.getAllValidUser();
		return list;
	}

	@Override
	public Page<SystemUser> selectAllbyParam(Map<String, Object> paramMap)
			throws Exception {
		Page<SystemUser> page = new Page<SystemUser>();
		
		int fetchSize = Integer.valueOf(paramMap.get("fetchSize").toString());
		int pageNumber = Integer.valueOf(paramMap.get("pageNumber").toString());
		int totalCount = systemUserMapper.getCountByPrimaryKey(paramMap);
		int pageCount = 0;
		if (totalCount%fetchSize == 0) {
			pageCount = totalCount/fetchSize;
		} else {
			pageCount = totalCount/fetchSize + 1;
		}
		try {
			List<SystemUser> list =systemUserMapper.selectByPrimaryKeyPage(paramMap);
			page.setList(list);
			page.setTotalCount( systemUserMapper.getCountByPrimaryKey(paramMap));
			page.setPageNumber(pageNumber);
			page.setFetchSize(fetchSize);
			page.setPageCount(pageCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public Integer update(SystemUser systemUser){
		return systemUserMapper.update(systemUser);
	}

	@Override
	public Integer delGuidinfo(int sid) {
		return systemUserMapper.delete(sid);
	}

	@Override
	public SystemUser checkIsUnique(Map<String, Object> map) {
		SystemUser SystemUser = this.systemUserMapper.checkIsUnique(map);
		return SystemUser;
	}
}
