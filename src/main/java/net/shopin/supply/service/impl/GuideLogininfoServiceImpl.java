package net.shopin.supply.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.shopin.supply.domain.entity.GuideLog;
import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.persistence.GuideLogMapper;
import net.shopin.supply.persistence.GuideLogininfoMapper;
import net.shopin.supply.persistence.GuideSupplyMapper;
import net.shopin.supply.service.IGuideLogininfoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
import com.shopin.core.framework.exception.ShopinException;
import com.shopin.core.util.ResultUtil;

@Service
public class GuideLogininfoServiceImpl implements IGuideLogininfoService {
	
	@Autowired
	private GuideLogininfoMapper guideLogininfoMapper;
	
	@Autowired
	private GuideLogMapper guideLogMapper;
	
	@Autowired
	private GuideSupplyMapper guideSupplyMapper;
	
	private Logger logger = Logger.getLogger(GuideLogininfoServiceImpl.class);

	@Override
	public List<GuideLogininfo> selectListByParam(Map paramMap) {
		
		List<GuideLogininfo> guideLogininfolist = this.guideLogininfoMapper.selectListByParam(paramMap);
		return guideLogininfolist;
	}

	/**
	 * 获取导购登录信息总数
	 */
	@Override
	public Integer getCountByParam(Map<String, Object> paramMap) {

		int count = this.guideLogininfoMapper.getCountByParam(paramMap);
		return count;
	}

	/**
	 * 保存导购登录信息
	 */
	@Override
	public Integer saveGuideLoginInfo(GuideLogininfo guideLogininfo) {
      
		Integer result = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		if(null == guideLogininfo.getSid()){
			result = this.guideLogininfoMapper.insert(guideLogininfo);
			if(result!=1){
				throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_ERROR.getMemo());
			}
		}else{
			result = this.guideLogininfoMapper.update(guideLogininfo);
			if(result!=1){
				throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
			}
		}
		return result;
	}

	/**
	 * 删除导购登录信息(只修改导购登录信息的有效状态)
	 */
	@Override
	public void updateValidBitStatus(GuideLogininfo guideLogininfo) {
//		SecurityContext ctx = SecurityContextHolder.getContext();         
//      Authentication auth = ctx.getAuthentication();
//      String user =auth.getName();
      
		Integer result = 0;
		try{
			result = this.guideLogininfoMapper.updateValidBitStatus(guideLogininfo);
			if(result!=1){
				throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
			}else{
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideLogininfo.getGuideNo());
				guideLog.setOperator("zhangqing");
//				guideLog.setOperator(user);
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("系统时间："+new Date()+" 用户'"+"zhangqing"+"'删除编号为'"+
						guideLogininfo.getGuideNo()+"'的导购登录信息");
				
				result = this.guideLogMapper.insert(guideLog);
				if(result!=1){
					throw new ShopinException(ErrorCode.SAVE_GuideLOG_ERROR.getErrorCode() + "_" + ErrorCode.SAVE_GuideLOG_ERROR.getMemo());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户名、密码查询导购登录信息
	 */
	@Override
	public GuideLogininfo selectGuideLoginByUsername(Map<String, Object> map) {
		
		GuideLogininfo guideLogininfo = this.guideLogininfoMapper.selectGuideLoginByUsername(map);
		return guideLogininfo;
	}

	/**
	 * 现有导购信息注册坚持胸卡编号是否唯一
	 */
	@Override
	public GuideLogininfo checkChestCardNumIsUnique(Map<String, Object> map) {
		
		GuideLogininfo guideLogininfo = this.guideLogininfoMapper.checkChestCardNumIsUnique(map);
		return guideLogininfo;
	}
	
	/**
	 * 保存导购胸卡编号、登录名、密码等信息
	 */
	@Override
	public Integer saveLogininfo(GuideLogininfo guideLogininfo,String username) {
		Integer result = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		if(null == guideLogininfo.getSid()){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			int shopId = guideLogininfo.getShopId();
			paramMap.put("shopId", shopId);
			int chestcardNum = 0;
			
			//查询门店shopId 的导购胸卡编号最大已经排序到多少
			List<GuideLogininfo> guideLogininfoList = this.guideLogininfoMapper.selectListByParam(paramMap);
			if(guideLogininfoList.size() == 0){
				chestcardNum = 00001;
			}else{
				for(int i=0;i<guideLogininfoList.size();i++){
					//新生成的编号规则为，已有编号最大值+1
					chestcardNum = guideLogininfoList.get(0).getChestcardNum()+1;
					break;
				}
			}
			
			String chestcardShoprule = guideLogininfo.getChestcardShoprule();
			String chestCardNumber = "";
//			if(shopId == 1301){//下沙店  下沙店胸卡规则（S1301+SAP的一级品类代码+三位数字顺序号，例如：S1301H160、S1301J170）
				
//				//根据门店id和导购编号 查询此导购绑定的一级品类代码
//				paramMap.put("guideNo", guideLogininfo.getGuideNo());
//				GuideSupply guideSupply = this.guideSupplyMapper.selectGuideSupplyByParam(paramMap);
//				int categoryId = 0;
//				if(null == guideSupply){
//					return 2;//根据下沙店胸卡规则 导购必须先绑定供应商，已知一级品类代码才能生成胸卡编号
//				}else{
//					int changeSupplyBit = guideSupply.getChangeSupplyBit();//导购是否转场
//					if(changeSupplyBit == 1){//是
//						categoryId = guideSupply.getChangeCategorysId();
//					}else{//不是
//						categoryId = guideSupply.getCategorysId();
//					}
//				}
//				
//				char categoryCode = (char)categoryId;//SAP的一级品类代码 是 由一级品类sid转化字符而来
				
//				chestCardNumber = chestcardShoprule+categoryCode+chestcardNum;
//			}else{
				chestCardNumber = chestcardShoprule+chestcardNum;
//			}
			
			guideLogininfo.setChestcardNum(chestcardNum);
			guideLogininfo.setChestcardNumber(chestCardNumber);
			guideLogininfo.setLoginUsername(chestCardNumber);
			guideLogininfo.setLoginPassword(chestCardNumber.substring(chestCardNumber.length()-4));
			result = this.guideLogininfoMapper.insert(guideLogininfo);
			if(result!=1){
				return 0;
			}else{
				String jsonData = ResultUtil.createSuccessResult(guideLogininfo);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideLogininfo.getGuideNo());
				guideLog.setOperator(username);
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("生成胸卡编号："+jsonData);
//				guideLog.setDescription("系统时间："+df.format(new Date())+" 系统用户'"+username+"'为编号为'"+
//						guideLogininfo.getGuideNo()+"'的导购生成胸卡编号");
				
				result = this.guideLogMapper.insert(guideLog);
				if(result!=1){
					return 0;
				}
			}
			
		}else{
			result = this.guideLogininfoMapper.update(guideLogininfo);
			if(result!=1){
				return 0;
			}else{
				String jsonData = ResultUtil.createSuccessResult(guideLogininfo);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideLogininfo.getGuideNo());
				guideLog.setOperator(username);
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("修改胸卡编号："+jsonData);
//				guideLog.setDescription("系统时间："+df.format(new Date())+" 系统用户： "+username+" 修改编号为： "+
//						guideLogininfo.getGuideNo()+" 的导购基本信息");
				
				result = this.guideLogMapper.insert(guideLog);
				if(result!=1){
					return 0;
				}
			}
		}
		return result;
	}
	
	/**
	 * 检查导购在某门店是否已有胸卡
	 */
	@Override
	public List<GuideLogininfo> selectGuideChestCartList(Map paramMap) {
		List<GuideLogininfo> list = this.guideLogininfoMapper.selectListByParam(paramMap);
		return list;
	}

	/**
	 * 删除导购胸卡
	 */
	@Override
	public Integer delGuideChestCard(long sid) {
		Integer result = 0;
		result = this.guideLogininfoMapper.delete(sid);
		if(result!=1){
			return 0;
		}
		return result;
	}


	@Override
	public GuideLogininfo checkIsUnique(Map<String, Object> map) {
		GuideLogininfo guideLogininfo = this.guideLogininfoMapper.checkIsUnique(map);
		return guideLogininfo;
	}
}
