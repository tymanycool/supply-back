package net.shopin.supply.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideLog;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.entity.SupplyInfo;
import net.shopin.supply.persistence.GuideLogMapper;
import net.shopin.supply.persistence.GuideSupplyMapper;
import net.shopin.supply.persistence.GuideinfoMapper;
import net.shopin.supply.persistence.SupplyinfoMapper;
import net.shopin.supply.service.IGuideSupplyService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
import com.shopin.core.framework.exception.ShopinException;
import com.shopin.core.util.ResultUtil;

/**
* @ClassName: GuideSupplyServiceImpl 
* @author zhangq
* @date 2014-12-24 上午11:35:03 
*
 */
@Service
public class GuideSupplyServiceImpl implements IGuideSupplyService {

	private Logger logger = Logger.getLogger(GuideSupplyServiceImpl.class);
	
	@Autowired
	private GuideSupplyMapper guideSupplyMapper;
	
	@Autowired
	private SupplyinfoMapper supplyinfoMapper;
	
	@Autowired
	private GuideLogMapper guideLogMapper;
	
	@Autowired
	private GuideinfoMapper guideinfoMapper;
	
	
	@Override
	public List<GuideSupply> selectListByParam(Map paramMap){
		List<GuideSupply> list = this.guideSupplyMapper.selectListByParam(paramMap);
		return list;
	}

	
	/**
	 * 导购绑定供应商和门店
	 */
	@Override
	public Integer saveGuideSupply(GuideSupply guideSupply,String username) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Integer result = 0;
		result = this.guideSupplyMapper.insert(guideSupply);
		if(result!=1){
			throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_ERROR.getMemo());
		}else{
			
			//供应商绑定成功修改导购基本信息表中操作人
			Guideinfo guideinfo = new Guideinfo();
			guideinfo.setOperator(username);
			guideinfo.setGuideNo(guideSupply.getGuideNo());
			this.guideinfoMapper.updateByGuideNo(guideinfo);
			
			//添加操作日志
			String jsonData = ResultUtil.createSuccessResult(guideSupply);
			GuideLog guideLog = new GuideLog();
			guideLog.setGuideNo(guideSupply.getGuideNo());
			guideLog.setOperator(username);
			guideLog.setOperatTime(new Date());
			guideLog.setDescription("为导购绑定供应商："+jsonData);
//			guideLog.setDescription("系统时间："+df.format(new Date())+" 系统用户："+username+" 为编号为："+
//					guideSupply.getGuideNo()+" 的导购绑定ID为："+guideSupply.getSupplyId()+" 的供应商");
			
			try{
				this.guideLogMapper.insert(guideLog);
			}catch (Exception e) {
				this.logger.error("日志保存失败！", e);
			}
		}
		return result;
	}

	/**
	 * 解除导购与供应商关系(删除)
	 */
	@Override
	public Integer delGuideSupply(int sid,String username) {
		
		Integer result = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sid", sid);
		GuideSupply guideSupply = null;
		List<GuideSupply> guideSupplyList = this.guideSupplyMapper.selectListByParam(map);
		if(guideSupplyList.size()>0){
			for(int i=0;i<guideSupplyList.size();i++){
				guideSupply = guideSupplyList.get(0);
			}
		
			result = this.guideSupplyMapper.delete(sid);
			if(result!=1){
				throw new ShopinException(ErrorCode.UPDATE_ERROR.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
			}else{
				
				//供应商绑定成功修改导购基本信息表中操作人
				Guideinfo guideinfo = new Guideinfo();
				guideinfo.setOperator(username);
				guideinfo.setGuideNo(guideSupply.getGuideNo());
				this.guideinfoMapper.updateByGuideNo(guideinfo);
				
				String jsonData = ResultUtil.createSuccessResult(guideSupply);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideSupply.getGuideNo());
				guideLog.setOperator(username);
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("为导购解除绑定供应商："+jsonData);
//				guideLog.setDescription("系统时间："+df.format(new Date())+" 系统用户："+username+" 解除编号为："+
//						guideSupply.getGuideNo()+" 的导购与供应商SID为："+guideSupply.getSupplyId()+" 的关系");
				
				try{
					this.guideLogMapper.insert(guideLog);
				}catch (Exception e) {
					this.logger.error("日志保存失败！", e);
				}
			}
		}
		return result;
	}


	/**
	 * 获取所有供应商信息
	 */
	@Override
	public List<SupplyInfo> selectSupplyInfoListByParam(Map paramMap) {
		
		List<SupplyInfo> supplyInfoList = this.supplyinfoMapper.selectListByParam(paramMap);
		return supplyInfoList;
	}


	/**
	 * 导购登录pad验证此导购是否绑定此pad供应商
	 */
	@Override
	public List<GuideSupply> loginSupplyValide(Map<String, Object> map) {
		
		List<GuideSupply> guideSupplyList = this.guideSupplyMapper.loginSupplyValide(map);
		return guideSupplyList;
	}

	/**
	 * 导购转场（此方法已失效）
	 */
	@Override
	public int changeGuideSupply(GuideSupply guideSupply, String username) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		int result = this.guideSupplyMapper.changeGuideSupply(guideSupply);
		if(result != 1){
			throw new ShopinException(ErrorCode.UPDATE_ERROR.getErrorCode() + "_导购转场失败!");
		}else{
			
			//供应商绑定成功修改导购基本信息表中操作人
			Guideinfo guideinfo = new Guideinfo();
			guideinfo.setOperator(username);
			guideinfo.setGuideNo(guideSupply.getGuideNo());
			this.guideinfoMapper.updateByGuideNo(guideinfo);
			
			//记录操作日志
			GuideLog guideLog = new GuideLog();
			guideLog.setGuideNo(guideSupply.getGuideNo());
			guideLog.setOperator(username);
			guideLog.setOperatTime(new Date());
			guideLog.setDescription("系统时间："+df.format(new Date())+" 系统用户："+username+" 为编号为："+
					guideSupply.getGuideNo()+" 的导购从供应商id为："+guideSupply.getSupplyId()+" 转场至供应商id为："+guideSupply.getChangeSupplyId());
			
			result = this.guideLogMapper.insert(guideLog);
			if(result!=1){
				throw new ShopinException(ErrorCode.SAVE_PADLOG_ERROR.getErrorCode() + "_导购转场保存log日志失败！");
			}
		}
		return result;
	}

	@Override
	public GuideSupply get(long sid) {
		
		GuideSupply GuideSupply = this.guideSupplyMapper.get(sid);
		return GuideSupply;
	}
	
	@Override
	public int saveBrandSSDSid(GuideSupply guideSupply) {
		
		int result = this.guideSupplyMapper.updateBrandSSDSid(guideSupply);
		return result;
	}
	@Override
	public int saveChangeBrandSSDSid(GuideSupply guideSupply) {
		
		int result = this.guideSupplyMapper.updateChangeBrandSSDSid(guideSupply);
		return result;
	}

	@Override
	public int update(GuideSupply guideSupply) {
		int result = this.guideSupplyMapper.updateValidBitStatus(guideSupply);
		return result;
	}

}
