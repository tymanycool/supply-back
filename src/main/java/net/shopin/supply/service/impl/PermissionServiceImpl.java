package net.shopin.supply.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.GuideLog;
import net.shopin.supply.domain.entity.Permission;
import net.shopin.supply.persistence.GuideLogMapper;
import net.shopin.supply.persistence.PermissionMapper;
import net.shopin.supply.service.IPermissionService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired 
	private PermissionMapper permissionMapper;
	@Autowired
	private GuideLogMapper guideLogMapper;
	
	private Logger logger = Logger.getLogger(PermissionServiceImpl.class);
	
	@Override
	public Integer save(Permission permission) {
		Integer result = 0;
		if(null == permission.getSid()){
			result = this.permissionMapper.insert(permission);
		}else{
			result = this.permissionMapper.updateByPrimaryKey(permission);
		}
		//插入变价权限修改记录-------------add by pengpu  start--------------
		GuideLog guideLog = new GuideLog();
		guideLog.setGuideNo(permission.getGuideNo());
		guideLog.setOperator(permission.getOperatoeName());
		guideLog.setOperatorId(permission.getOperatorId());
		guideLog.setOperatTime(new Date());
		guideLog.setType(1);
		guideLog.setTypeDesc("修改导购手动变价权限");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject json = new JSONObject();
		json.put("memo", "导购权限部分修改信息");
		json.put("ifopen", permission.getFlag());//是否开通
		json.put("startTime", sdf.format(permission.getStartTime()));//权限开始时间
		json.put("endTime", sdf.format(permission.getEndTime()));//权限结束时间
		guideLog.setDescription(json.toString());
//		guideLog.setDescription("系统时间："+df.format(new Date())+" 系统用户'"+guideinfo.getOperator()+"'修改编号为'"+
//				guide.getGuideNo()+"'的导购基本信息");
		try{
			this.guideLogMapper.insert(guideLog);
		}catch (Exception e) {
			this.logger.error("日志保存失败！", e);
		}
		//插入变价权限修改记录-------------add by pengpu  end----------------
		
		return result;
	}

	@Override
	public Permission selectByParam(Map<String, Object> map) {
		Permission permission = this.permissionMapper.selectByParam(map);
		return permission;
	}
	
	
}
