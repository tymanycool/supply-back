package net.shopin.supply.service.impl;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.shopin.supply.domain.entity.GuideLog;
import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.entity.GuideinfoAttach;
import net.shopin.supply.domain.vo.GuideinfoVO;
import net.shopin.supply.persistence.GuideLogMapper;
import net.shopin.supply.persistence.GuideLogininfoMapper;
import net.shopin.supply.persistence.GuideSupplyMapper;
import net.shopin.supply.persistence.GuideinfoAttachMapper;
import net.shopin.supply.persistence.GuideinfoMapper;
import net.shopin.supply.service.IGuideinfoService;
import net.shopin.supply.util.ExcelFile;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopin.core.constants.ErrorCodeConstants.ErrorCode;
import com.shopin.core.framework.exception.ShopinException;
import com.shopin.core.util.ResultUtil;

@Service
public class GuideinfoServiceImpl implements IGuideinfoService {

	private Logger logger = Logger.getLogger(GuideinfoServiceImpl.class);
	
	@Autowired
	private GuideinfoMapper guideinfoMapper;
	
	@Autowired
	private GuideinfoAttachMapper guideinfoAttachMapper;
	
	@Autowired
	private GuideLogMapper guideLogMapper;
	
	@Autowired
	private GuideSupplyMapper guideSupplyMapper;
	
	@Autowired
	private GuideLogininfoMapper guideLogininfoMapper;
	
	/**
	 * 获取导购基本信息和对应导购胸卡编号信息列表(带分页)
	 */
	@Override
	public List<GuideinfoVO> getAllGuideInfoListPage(Map paramMap){
		List<GuideinfoVO> list = this.guideinfoMapper.getAllGuideInfoListPage(paramMap);
		return list;
	} 
	
	/**
	 * 获取导购基本信息和对应导购胸卡编号信息列表
	 */
	@Override
	public List<GuideinfoVO> getAllGuideInfoList(Map paramMap) {
		List<GuideinfoVO> list = this.guideinfoMapper.getAllGuideInfoList(paramMap);
		return list;
	}
	
	@Override
	public List<Guideinfo> selectListByParam(Map paramMap){
		List<Guideinfo> list = this.guideinfoMapper.selectListByParam(paramMap);
		return list;
	}
	
	/**
	 * 通过供应商门店获取导购组
	 */
	@Override
	public List<Guideinfo> selectGuideListByParam(Map paramMap){
		List<Guideinfo> list = this.guideinfoMapper.selectGuideListByParam(paramMap);
		return list;
	}

	/**
	 * 保存
	 */
	@Override
	public Integer save(Guideinfo guideinfo) {
        
		Integer result = 0;
		if(null == guideinfo.getSid()){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			int guideNo = 0;
			List<Guideinfo> padGuideinfo = this.guideinfoMapper.selectListByParam(paramMap);
			if(padGuideinfo.size() == 0){
				guideNo = 001;
			}else{
				for(int i=0;i<padGuideinfo.size();i++){
					guideNo = padGuideinfo.get(0).getGuideNo()+1;
					break;
				}
			}
			guideinfo.setGuideNo(guideNo);
			result = this.guideinfoMapper.insert(guideinfo);
			if(result!=1){
				return 0;
			}else{
				String jsonData = ResultUtil.createSuccessResult(guideinfo);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideinfo.getGuideNo());
				guideLog.setOperator(guideinfo.getOperator());
				guideLog.setOperatTime(new Date());
//				guideLog.setDescription("系统时间："+df.format(new Date())+" 用户'"+guideinfo.getName()+"'添加编号为'"+
//						guideinfo.getGuideNo()+"'的导购基本信息");
				guideLog.setDescription("添加导购基本信息:"+jsonData);
				
				try{
					this.guideLogMapper.insert(guideLog);
				}catch (Exception e) {
					this.logger.error("日志保存失败！", e);
				}
			}
			
		}else{
			result = this.guideinfoMapper.update(guideinfo);
			if(result!=1){
				return 0;
			}else{
				String jsonData = ResultUtil.createSuccessResult(guideinfo);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideinfo.getGuideNo());
				guideLog.setOperator(guideinfo.getOperator());
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("修改导购基本信息："+jsonData);
//				guideLog.setDescription("系统时间："+df.format(new Date())+" 用户'"+guideinfo.getOperator()+"'修改编号为'"+
//						guideinfo.getGuideNo()+"'的导购基本信息");
				
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
	 * 保存导购信息
	 */
	@Override
	public Integer saveGuideInfo(Guideinfo guideinfo,String familyObj,String workObj) {
        
		Integer result = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		if(null == guideinfo.getSid()){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			int guideNo = 0;
			List<Guideinfo> padGuideinfo = this.guideinfoMapper.selectListByParam(paramMap);
			if(padGuideinfo.size() == 0){
				guideNo = 001;
			}else{
				for(int i=0;i<padGuideinfo.size();i++){
					guideNo = padGuideinfo.get(0).getGuideNo()+1;
					break;
				}
			}
			guideinfo.setGuideNo(guideNo);
			guideinfo.setOperator(guideinfo.getName());
			guideinfo.setCreatetime(new Date());
			result = this.guideinfoMapper.insert(guideinfo);
			if(result!=1){
				
				return 0;
				
			}else{//基本导购注册附属信息(家庭成员、工作经验等信息)
				
				JSONArray familyData = JSONArray.fromObject(familyObj);
				String familyName = "";
				String relation = "";
				String familyMobile = "";
				for(int i=0;i<familyData.size();i++){
					GuideinfoAttach guideinfoAttach = new GuideinfoAttach();
				     JSONObject familyJson =  (JSONObject) familyData.get(i);
				     familyName = familyJson.getString("familyName");
				     relation = familyJson.getString("relation");
				     familyMobile = familyJson.getString("familyMobile");
				     
				     guideinfoAttach.setFamilyName(familyName);
				     guideinfoAttach.setRelation(relation);
				     guideinfoAttach.setMobile(familyMobile);
				     guideinfoAttach.setType(1);//1：家庭成员信息
				     guideinfoAttach.setGuideNo(guideNo);
				     guideinfoAttach.setCreattime(new Date());
				     
				     this.guideinfoAttachMapper.insert(guideinfoAttach);
				}
				
				if(null != workObj && !workObj.equals("")){
					JSONArray workData = JSONArray.fromObject(workObj);
					String workStarttime = "";
					String workEndtime = "";
					String company = "";
					String position = "";
					String leaveResult = "";
					for(int i=0;i<workData.size();i++){
						JSONObject workJson =  (JSONObject) workData.get(i);
						workStarttime = workJson.getString("workStarttime");
						String newWorkStarttime = workStarttime.replace("GMT+0800","GMT+08:00");
						workEndtime = workJson.getString("workEndtime");
						String newWorkEndtime = workEndtime.replace("GMT+0800","GMT+08:00");
						company = workJson.getString("company");
						position = workJson.getString("position");
						leaveResult = workJson.getString("leaveResult");
						
						try {
							GuideinfoAttach guideinfoAttach = new GuideinfoAttach();
							SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z",Locale.ENGLISH);
							guideinfoAttach.setWorkStarttime(sf.parse(newWorkStarttime));
							guideinfoAttach.setWorkEndtime(sf.parse(newWorkEndtime));
							guideinfoAttach.setCompany(company);
							guideinfoAttach.setPosition(position);
							guideinfoAttach.setLeaveResult(leaveResult);
							guideinfoAttach.setType(2);//2：工作经历信息
							guideinfoAttach.setGuideNo(guideNo);
							guideinfoAttach.setCreattime(new Date());
							
							this.guideinfoAttachMapper.insert(guideinfoAttach);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}else{
			Guideinfo guide = this.guideinfoMapper.selectByPrimaryKey(guideinfo.getSid());
			guideinfo.setOperator(guideinfo.getOperator());
			result = this.guideinfoMapper.update(guideinfo);
			if(result!=1){
				return 0;
			}else{
				String jsonData = ResultUtil.createSuccessResult(guideinfo);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guide.getGuideNo());
				guideLog.setOperator(guideinfo.getOperator());
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("修改导购基本信息："+jsonData);
//				guideLog.setDescription("系统时间："+df.format(new Date())+" 系统用户'"+guideinfo.getOperator()+"'修改编号为'"+
//						guide.getGuideNo()+"'的导购基本信息");
				
				//Add by qutengfei For 供应商管理平台导购信息修改,插入修改记录的时候添加上修改的类型 IN 20150717 Start
				/**
				* 
				* for bug
				* feature https://tower.im/s/24CeG
				* author qutengfei
				*/
				guideLog.setType(2);
				guideLog.setTypeDesc("修改导购基本信息");
				//Add by qutengfei For 供应商管理平台导购信息修改,插入修改记录的时候添加上修改的类型 IN 20150717 End
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
	 * 现有导购注册信息保存
	 */
	@Override
	public Integer existingGuideinfosave(Guideinfo guideinfo,String familyObj,String workObj,String chestcardNum,String shop) {
        
		Integer result = 0;
		if(null == guideinfo.getSid()){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			int guideNo = 0;
			List<Guideinfo> padGuideinfo = this.guideinfoMapper.selectListByParam(paramMap);
			if(padGuideinfo.size() == 0){
				guideNo = 001;
			}else{
				for(int i=0;i<padGuideinfo.size();i++){
					guideNo = padGuideinfo.get(0).getGuideNo()+1;
					break;
				}
			}
			guideinfo.setGuideNo(guideNo);
			guideinfo.setOperator(guideinfo.getName());
			result = this.guideinfoMapper.insert(guideinfo);
			if(result!=1){
				
				return 0;
				
			}else{//基本导购注册附属信息(家庭成员、工作经验等信息)
				
				JSONArray familyData = JSONArray.fromObject(familyObj);
				String familyName = "";
				String relation = "";
				String familyMobile = "";
				for(int i=0;i<familyData.size();i++){
					GuideinfoAttach guideinfoAttach = new GuideinfoAttach();
				     JSONObject familyJson =  (JSONObject) familyData.get(i);
				     familyName = familyJson.getString("familyName");
				     relation = familyJson.getString("relation");
				     familyMobile = familyJson.getString("familyMobile");
				     
				     guideinfoAttach.setFamilyName(familyName);
				     guideinfoAttach.setRelation(relation);
				     guideinfoAttach.setMobile(familyMobile);
				     guideinfoAttach.setType(1);//1：家庭成员信息
				     guideinfoAttach.setGuideNo(guideNo);
				     guideinfoAttach.setCreattime(new Date());
				     
				     result =this.guideinfoAttachMapper.insert(guideinfoAttach);
				     if(result!=1){
						return 0;
					}
				}
				
				if(null != workObj && !workObj.equals("")){
					JSONArray workData = JSONArray.fromObject(workObj);
					String workStarttime = "";
					String workEndtime = "";
					String company = "";
					String position = "";
					String leaveResult = "";
					for(int i=0;i<workData.size();i++){
						JSONObject workJson =  (JSONObject) workData.get(i);
						workStarttime = workJson.getString("workStarttime");
						String newWorkStarttime = workStarttime.replace("GMT+0800","GMT+08:00");
						workEndtime = workJson.getString("workEndtime");
						String newWorkEndtime = workEndtime.replace("GMT+0800","GMT+08:00");
						company = workJson.getString("company");
						position = workJson.getString("position");
						leaveResult = workJson.getString("leaveResult");
						
						try {
							GuideinfoAttach guideinfoAttach = new GuideinfoAttach();
							SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z",Locale.ENGLISH);
							guideinfoAttach.setWorkStarttime(sf.parse(newWorkStarttime));
							guideinfoAttach.setWorkEndtime(sf.parse(newWorkEndtime));
							guideinfoAttach.setCompany(company);
							guideinfoAttach.setPosition(position);
							guideinfoAttach.setLeaveResult(leaveResult);
							guideinfoAttach.setType(2);//2：工作经历信息
							guideinfoAttach.setGuideNo(guideNo);
							guideinfoAttach.setCreattime(new Date());
							
							result =this.guideinfoAttachMapper.insert(guideinfoAttach);
							if(result!=1){
								return 0;
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				//胸卡编号、pad登录等信息
				String chestcardShoprule = "";
				if(null != chestcardNum && !chestcardNum.equals("")){
					String password = chestcardNum.substring(chestcardNum.length()-4);
					String chestcardNumber = chestcardNum.toUpperCase();
					String username = chestcardNum.toUpperCase();
					
					GuideLogininfo guideLogininfo = new GuideLogininfo();
					if(null != shop && !shop.equals("")){
						String[] shopArr = shop.split("_");
						String shopid = shopArr[0];
						String shopname = shopArr[1];
//						if(shopid.equals("1010") || shopid.equals("1011")){//1010回龙观店1011草桥店
						if(shopid.equals("1011")){//2015-3-13 变更：回龙观的门店规则也是前三位 S10
							chestcardShoprule = "S"+chestcardNumber.substring(1, 4);
							chestcardNumber =  chestcardNumber.substring(4);
						}else if(shopid.equals("1301")){//下沙店   下沙店胸卡规则（S1301+SAP的一级品类代码+三位数字顺序号，例如：S1301H160、S1301J170）
							chestcardShoprule = chestcardNumber.substring(0, 6);
							chestcardNumber =  chestcardNumber.substring(6);
						}else if(shopid.equals("1311")){//笕桥店  规则 S1311+系统自动生成编号
							chestcardShoprule = chestcardNumber.substring(0, 5);
							chestcardNumber =  chestcardNumber.substring(5);
						}else{
							chestcardShoprule = "S"+chestcardNumber.substring(1, 3);
							chestcardNumber =  chestcardNumber.substring(3);
						}
						guideLogininfo.setShopId(Integer.parseInt(shopid));
						guideLogininfo.setShopName(shopname);
					}
					guideLogininfo.setGuideNo(guideNo);
					guideLogininfo.setCreattime(new Date());
					guideLogininfo.setChestcardShoprule(chestcardShoprule);
					guideLogininfo.setChestcardNum(Integer.parseInt(chestcardNumber));
					guideLogininfo.setLoginUsername(username);
					guideLogininfo.setLoginPassword(password);
					guideLogininfo.setChestcardNumber(chestcardNum.toUpperCase());
					guideLogininfo.setValidBit(1);//是否有效 0:无效 1:有效
					
					result = this.guideLogininfoMapper.insert(guideLogininfo);
					if(result!=1){
						return 0;
					}
				}
			}
			
		}
		return result;
	}

	@Override
	public Integer updateByGuideNo(Guideinfo guideinfo) {
		
		return this.guideinfoMapper.updateByGuideNo(guideinfo);
	}

	/**
	 * 删除导购信息(只修改导购信息的有效状态)
	 */
	@Override
	public String updateValidBitStatus(Guideinfo guideinfo) {
        
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Integer result = 0;
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			if(guideinfo.getValidBit().equals(0)){
				map.put("validbit","1");
			}
			if(guideinfo.getValidBit().equals(1)){
				map.put("validbit","0");
			}
			map.put("guideNo", guideinfo.getGuideNo());
			List<GuideSupply> guideSupplyList = this.guideSupplyMapper.selectListByParam(map);
			if(guideSupplyList.size() > 0){
				
				for(GuideSupply guideSupply : guideSupplyList){
					guideSupply.setValidBit(guideinfo.getValidBit());//是否有效 0:无效 1:有效
					result = this.guideSupplyMapper.updateValidBitStatus(guideSupply);
					if(result!=1){
						if(guideinfo.getValidBit().equals("0")){
							return ResultUtil.createFailureResult("00000", "删除导购时删除导购下绑定供应商信息失败！");
						}else{
							return ResultUtil.createFailureResult("00000", "恢复导购时恢复导购下绑定供应商信息失败！");
						}
						
					}else{
						String jsonData = ResultUtil.createSuccessResult(guideSupply);
						GuideLog guideLog = new GuideLog();
						guideLog.setGuideNo(guideinfo.getGuideNo());
						guideLog.setOperator(guideinfo.getOperator());
						guideLog.setOperatTime(new Date());
						guideLog.setDescription("删除导购下绑定供应商信息："+jsonData);
						
						try{
							this.guideLogMapper.insert(guideLog);
						}catch (Exception e) {
							this.logger.error("删除导购时添加解绑导购与供应商Log信息失败！", e);
						}
					}
				}
			}
			
			List<GuideLogininfo> guideLogininfoList = this.guideLogininfoMapper.selectByGuideNo(map);
			if(guideLogininfoList.size()>0){
				for(GuideLogininfo guideLogininfo : guideLogininfoList){
					guideLogininfo.setValidBit(guideinfo.getValidBit());
					result = this.guideLogininfoMapper.updateValidBitStatus(guideLogininfo);
					if(result != 1){
						if(guideinfo.getValidBit().equals("0")){
							return ResultUtil.createFailureResult("00000", "删除导购时删除导购胸卡编号信息失败！");
						}else{
							return ResultUtil.createFailureResult("00000", "恢复导购时恢复导购胸卡编号信息失败！");
						}
						
					}else{
						String jsonData = ResultUtil.createSuccessResult(guideLogininfo);
						GuideLog guideLog = new GuideLog();
						guideLog.setGuideNo(guideinfo.getGuideNo());
						guideLog.setOperator(guideinfo.getOperator());
						guideLog.setOperatTime(new Date());
						guideLog.setDescription("删除导购时删除导购胸卡编号信息："+jsonData);
						
						try{
							this.guideLogMapper.insert(guideLog);
						}catch (Exception e) {
							this.logger.error("删除导购时删除导购胸卡编号息失败！", e);
						}
					}
				}
			}
			
			result = this.guideinfoMapper.updateValidBitStatus(guideinfo);
			if(result!=1){
				if(guideinfo.getValidBit().equals("0")){
					return ResultUtil.createFailureResult("00000", "删除导购基本信息失败！");
				}else{
					return ResultUtil.createFailureResult("00000", "恢复导购基本信息失败！");
				}
			}else{
				String jsonData = ResultUtil.createSuccessResult(guideinfo);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideinfo.getGuideNo());
				guideLog.setOperator(guideinfo.getOperator());
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("删除导购基本信息："+jsonData);
				
				try{
					this.guideLogMapper.insert(guideLog);
				}catch (Exception e) {
					this.logger.error("删除导购时添加删除导购Log信息失败！", e);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 根据id获取导购自助信息明细
	 */
	@Override
	public Guideinfo selectByPrimaryKey(int sid) {
      
		Guideinfo guideinfo= this.guideinfoMapper.selectByPrimaryKey(sid);
//		Map<String, Object> map = new HashMap<String, Object>();
//		String json = "";
//		if(null != guideinfo){
//			
//			map.put("guideinfo", guideinfo);
//			
//			int guideNo = guideinfo.getGuideNo();
//			Map<String, Object> familyMapParam = new HashMap<String, Object>();
//			familyMapParam.put("guideNo", guideNo);
//			familyMapParam.put("type", "1");
//			List<GuideinfoAttach> familyInfoList = this.guideinfoAttachMapper.selectListByParam(familyMapParam);
//			if(familyInfoList.size()>0){
//				map.put("familyMap", familyInfoList);
//			}
//			
//			Map<String, Object> workMapParam = new HashMap<String, Object>();
//			workMapParam.put("guideNo", guideNo);
//			workMapParam.put("type", "2");
//			List<GuideinfoAttach> workList = this.guideinfoAttachMapper.selectListByParam(workMapParam);
//			if(workList.size()>0){
//				map.put("workMap", workList);
//			}
//		}
//		
//		json = ResultUtil.createSuccessResult(map);
		
		return guideinfo;
	}
	
	@Override
	public List<GuideinfoAttach> selectFamilyByguideNo(Map map) {
		
		List<GuideinfoAttach> guideinfoAttachList= this.guideinfoAttachMapper.selectListByParam(map);
		
		return guideinfoAttachList;
	}

	@Override
	public Integer saveGuideAttach(GuideinfoAttach guideinfoAttach) {
		 
		Integer result = 0;
		result = this.guideinfoAttachMapper.insert(guideinfoAttach);
		if(result!=1){
			throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
		}
		return result;
	}

	@Override
	public Integer deleteGuideAttach(long sid) {
		
		Integer result = 0;
		result = this.guideinfoAttachMapper.delete(sid);
		if(result!=1){
			throw new ShopinException(ErrorCode.SAVE_ERROR.getErrorCode() + "_" + ErrorCode.UPDATE_ERROR.getMemo());
		}
		return result;
	}

	

	/**
	 * 根据导购编号查询导购信息
	 */
//	@Override
//	public GuideLogininfo selectByGuideNo(Map paramMap) {
//		GuideLogininfo guideLogininfo= this.guideLogininfoMapper.selectByGuideNo(paramMap);
//		return guideLogininfo;
//	}

	/**
	 * 获取导购信息总数
	 */
	@Override
	public Integer getCountByParam(Map<String, Object> paramMap) {
		int count = this.guideinfoMapper.getCountByParam(paramMap);
		return count;
	}

	/**
	 * 删除导购信息
	 */
	@Override
	public Integer delGuidinfo(int sid) {
		Integer result = 0;
		try{
			result  = this.guideinfoMapper.delete(sid);
			if(result != 1){
				return 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 按门店查询导购信息列表(带分页)
	 */
	@Override
	public List<GuideinfoVO> getAllGuideInfoListByShopPage(
			Map<String, Object> paramMap) {
		
		List<GuideinfoVO> guideinfoList = this.guideinfoMapper.getAllGuideInfoListByShopPage(paramMap);
		return guideinfoList;
	}
	
	/**
	 * 按门店查询导购信息列表
	 */
	@Override
	public List<GuideinfoVO> getAllGuideInfoListByShop(
			Map<String, Object> paramMap) {
		
		List<GuideinfoVO> guideinfoList = this.guideinfoMapper.getAllGuideInfoListByShop(paramMap);
		return guideinfoList;
	}

	/**
	 * 按门店查询导购信息总条数
	 */
	@Override
	public Integer getCountByParamByShop(Map<String, Object> paramMap) {

		int count = this.guideinfoMapper.getCountByParamByShop(paramMap);
		return count;
	}

	/**
	 * 按供应商查询导购信息列表(带分页)
	 */
	@Override
	public List<GuideinfoVO> getAllGuideInfoListBySupplyPage(
			Map<String, Object> paramMap) {
		
		List<GuideinfoVO> guideinfoList = this.guideinfoMapper.getAllGuideInfoListBySupplyPage(paramMap);
		return guideinfoList;
		
	}
	
	/**
	 * 按供应商查询导购信息列表
	 */
	@Override
	public List<GuideinfoVO> getAllGuideInfoListBySupply(
			Map<String, Object> paramMap) {
		
		List<GuideinfoVO> guideinfoList = this.guideinfoMapper.getAllGuideInfoListBySupply(paramMap);
		return guideinfoList;
		
	}
	
	/**
	 * 导购手动变价权限job查询
	 */
	@Override
	public List<GuideinfoVO> getAllGuideInfoListForAuthorizeGuideJob(
			Map<String, Object> paramMap) {
		
		List<GuideinfoVO> guideinfoList = this.guideinfoMapper.getAllGuideInfoListForAuthorizeGuideJob(paramMap);
		return guideinfoList;
		
	}

	/**
	 * 按供应商查询导购信息总条数
	 */
	@Override
	public Integer getCountByParamBySupply(Map<String, Object> paramMap) {

		int count = this.guideinfoMapper.getCountByParamBySupply(paramMap);
		return count;
	}

	/**
	 * 导出导购基本信息excel
	 */
	@Override
	public String exportGuideinfoToExcel(HttpServletResponse response,List<GuideinfoVO> list,String type) {
		
		List<String> header = new ArrayList<String>();
		List<List<String>> data = new ArrayList<List<String>>();

		String title = "";
		if(type.equals("1")){//导购信息管理中导出EXCEL
			
			title = "导购基本信息管理";
			
			header.add("姓名");
			header.add("胸卡编号");
			header.add("门店名称");
			header.add("性别");
			header.add("手机号码");
			header.add("身份证号");
			header.add("家庭住址");
			header.add("是否是导购");
			header.add("导购状态");
			header.add("是否有效");
			header.add("操作人");
			header.add("创建时间");
			
			String guideBitText = "";
			String guideStatusText = "";
			String guideValidBitText = "";
			for(GuideinfoVO guideinfo:list){
				List<String> inlist = new ArrayList<String>();
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
				
				inlist.add(guideinfo.getName() == null?"":guideinfo.getName());
				inlist.add(guideinfo.getChestcardNumber() == null?"":guideinfo.getChestcardNumber());
				inlist.add(guideinfo.getShopName() == null?"":guideinfo.getShopName());
				inlist.add(guideinfo.getSex() == null?"":guideinfo.getSex());
				inlist.add(guideinfo.getMobile() == null?"":guideinfo.getMobile());
				inlist.add(guideinfo.getGuideCard() == null?"":guideinfo.getGuideCard());
				inlist.add(guideinfo.getPresentAddress() == null?"":guideinfo.getPresentAddress());
				if(guideinfo.getGuideBit() == 0){//是否是导购 0:不是 1:是
					guideBitText = "不是";
				}else if(guideinfo.getGuideBit() == 1){
					guideBitText = "是";
				}
				inlist.add(guideBitText);
				if(guideinfo.getGuideStatus() == 0){//导购状态 0：初始状态；1：在柜；2不在柜
					guideStatusText = "初始状态";
				}else if(guideinfo.getGuideStatus() == 1){
					guideStatusText = "在柜";
				}else if(guideinfo.getGuideStatus() == 2){
					guideStatusText = "不在柜";
				}
				inlist.add(guideStatusText);
				if(guideinfo.getValidBit() == 0){//是否有效 0:无效 1:有效
					guideValidBitText = "无效";
				}else if(guideinfo.getValidBit() == 1){
					guideValidBitText = "有效";
				}
				inlist.add(guideValidBitText);
				inlist.add(guideinfo.getOperator() == null?"":guideinfo.getOperator());
				inlist.add(guideinfo.getCreatetime() == null?"":sdf.format(guideinfo.getCreatetime()));
				data.add(inlist);
			}
		}else if(type.equals("2")){//按门店统计查询中导出EXCEL
			
			title = "按门店统计导购信息";
			
			header.add("姓名");
			header.add("胸卡编号");
			header.add("门店");
			header.add("性别");
			header.add("手机号码");
			header.add("身份证号");
			header.add("家庭住址");
			header.add("是否是导购");
			header.add("导购状态");
			header.add("是否有效");
			header.add("暂住证有效截止时间");
			header.add("健康证有效截止时间");
			header.add("创建时间");
			
			String guideBitText = "";
			String guideStatusText = "";
			String guideValidBitText = "";
			for(GuideinfoVO guideinfo:list){
				List<String> inlist = new ArrayList<String>();
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
				
				inlist.add(guideinfo.getName() == null?"":guideinfo.getName());
				inlist.add(guideinfo.getChestcardNumber() == null?"":guideinfo.getChestcardNumber());
				inlist.add(guideinfo.getShopName() == null?"":guideinfo.getShopName());
				inlist.add(guideinfo.getSex() == null?"":guideinfo.getSex());
				inlist.add(guideinfo.getMobile() == null?"":guideinfo.getMobile());
				inlist.add(guideinfo.getGuideCard() == null?"":guideinfo.getGuideCard());
				inlist.add(guideinfo.getPresentAddress() == null?"":guideinfo.getPresentAddress());
				if(guideinfo.getGuideBit() == 0){//是否是导购 0:不是 1:是
					guideBitText = "不是";
				}else if(guideinfo.getGuideBit() == 1){
					guideBitText = "是";
				}
				inlist.add(guideBitText);
				if(guideinfo.getGuideStatus() == 0){//导购状态 0：初始状态；1：在柜；2不在柜
					guideStatusText = "初始状态";
				}else if(guideinfo.getGuideStatus() == 1){
					guideStatusText = "在柜";
				}else if(guideinfo.getGuideStatus() == 2){
					guideStatusText = "不在柜";
				}
				inlist.add(guideStatusText);
				if(guideinfo.getValidBit() == 0){//是否有效 0:无效 1:有效
					guideValidBitText = "无效";
				}else if(guideinfo.getValidBit() == 1){
					guideValidBitText = "有效";
				}
				inlist.add(guideValidBitText);
				inlist.add(guideinfo.getKitasEndtime() == null?"":sdf.format(guideinfo.getKitasEndtime()));
				inlist.add(guideinfo.getHealthCartEndtime() == null?"":sdf.format(guideinfo.getHealthCartEndtime()));
				inlist.add(guideinfo.getCreatetime() == null?"":sdf.format(guideinfo.getCreatetime()));
				data.add(inlist);
			}
		}else if(type.equals("3")){//按供应商统计导购中导出EXCEL
			
			title = "按供应商统计导购信息";
			
			header.add("姓名");
			header.add("门店");
			header.add("供应商编码");
			header.add("供应商");
			header.add("品牌");
			header.add("品类");
			header.add("性别");
			header.add("手机号码");
			header.add("身份证号");
			header.add("家庭住址");
			header.add("是否是导购");
			header.add("导购状态");
			header.add("是否有效");
			header.add("创建时间");
			header.add("是否有手动变价权限");
			header.add("导购胸卡编号");
			header.add("手动变价权限开始时间");
			header.add("手动变价权限截止时间");
			header.add("手动变价权限操作人");
			header.add("手动变价权限操作时间");
			
			String guideBitText = "";
			String guideStatusText = "";
			String guideValidBitText = "";
			String authorizeText = "";
			for(GuideinfoVO guideinfo:list){
				List<String> inlist = new ArrayList<String>();
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
				
				inlist.add(guideinfo.getName() == null?"":guideinfo.getName());
				inlist.add(guideinfo.getShopName() == null?"":guideinfo.getShopName());
				inlist.add(guideinfo.getSupplyId() == null?"":guideinfo.getSupplyId().toString());
				inlist.add(guideinfo.getSupplyName() == null?"":guideinfo.getSupplyName());
				inlist.add(guideinfo.getBrand() == null?"":guideinfo.getBrand());
				inlist.add(guideinfo.getCategroys() == null?"":guideinfo.getCategroys());
				inlist.add(guideinfo.getSex() == null?"":guideinfo.getSex());
				inlist.add(guideinfo.getMobile() == null?"":guideinfo.getMobile());
				inlist.add(guideinfo.getGuideCard() == null?"":guideinfo.getGuideCard());
				inlist.add(guideinfo.getPresentAddress() == null?"":guideinfo.getPresentAddress());
				if(guideinfo.getGuideBit() == 0){//是否是导购 0:不是 1:是
					guideBitText = "不是";
				}else if(guideinfo.getGuideBit() == 1){
					guideBitText = "是";
				}
				inlist.add(guideBitText);
				if(guideinfo.getGuideStatus() == 0){//导购状态 0：初始状态；1：在柜；2不在柜
					guideStatusText = "初始状态";
				}else if(guideinfo.getGuideStatus() == 1){
					guideStatusText = "在柜";
				}else if(guideinfo.getGuideStatus() == 2){
					guideStatusText = "不在柜";
				}
				inlist.add(guideStatusText);
				if(guideinfo.getValidBit() == 0){//是否有效 0:无效 1:有效
					guideValidBitText = "无效";
				}else if(guideinfo.getValidBit() == 1){
					guideValidBitText = "有效";
				}
				inlist.add(guideValidBitText);
				inlist.add(guideinfo.getCreatetime() == null?"":sdf.format(guideinfo.getCreatetime()));
				if(null != guideinfo.getFlag() && guideinfo.getFlag() == 0){//是否有手动添加补单的权限（1 有 0 否）
					authorizeText = "否";
				}else if(null != guideinfo.getFlag() && guideinfo.getFlag() == 1){
					authorizeText = "是";
				}else{
					authorizeText = "否";
				}
				inlist.add(authorizeText);
				inlist.add(guideinfo.getChestcardNumber() == null?"":guideinfo.getChestcardNumber());
				inlist.add(guideinfo.getStartTime() == null?"":sdf.format(guideinfo.getStartTime()));
				inlist.add(guideinfo.getEndTime() == null?"":sdf.format(guideinfo.getEndTime()));
				inlist.add(guideinfo.getOperatoeName() == null?"":guideinfo.getOperatoeName());
				inlist.add(guideinfo.getOperatTime() == null?"":sdf.format(guideinfo.getOperatTime()));
				data.add(inlist);
			}
		}
		
		ExcelFile ef = new ExcelFile(title, header, data);
		try {
			OutputStream file = response.getOutputStream();
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM"); 
			response.setHeader("Content-disposition","attachment; filename=/"+ new String(title.getBytes("gb2312"), "ISO8859-1" ) +".xls");
			ef.save(file);
			
			return "成功";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	public GuideinfoVO selectByGuideNo(int guideNo) {
		GuideinfoVO guideinfo= this.guideinfoMapper.selectByGuideNo(guideNo);
		return guideinfo;
	}

	@Override
	public String delValidBitStatus(Guideinfo guideinfo) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		Integer result = 0;
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("guideNo", guideinfo.getGuideNo());
			List<GuideSupply> guideSupplyList = this.guideSupplyMapper.selectListByParam(map);
			if(guideSupplyList.size() > 0){
				
				for(GuideSupply guideSupply : guideSupplyList){
					guideSupply.setValidBit(guideinfo.getValidBit());//是否有效 0:无效 1:有效
					result = this.guideSupplyMapper.updateValidBitStatus(guideSupply);
					if(result!=1){
						return ResultUtil.createFailureResult("00000", "删除导购时删除导购下绑定供应商信息失败！");
					}else{
						String jsonData = ResultUtil.createSuccessResult(guideSupply);
						GuideLog guideLog = new GuideLog();
						guideLog.setGuideNo(guideinfo.getGuideNo());
						guideLog.setOperator(guideinfo.getOperator());
						guideLog.setOperatTime(new Date());
						guideLog.setDescription("删除导购下绑定供应商信息："+jsonData);
						
						try{
							this.guideLogMapper.insert(guideLog);
						}catch (Exception e) {
							this.logger.error("删除导购时添加解绑导购与供应商Log信息失败！", e);
						}
					}
				}
			}
			
			List<GuideLogininfo> guideLogininfoList = this.guideLogininfoMapper.selectByGuideNo(map);
			if(guideLogininfoList.size()>0){
				for(GuideLogininfo guideLogininfo : guideLogininfoList){
					guideLogininfo.setValidBit(guideinfo.getValidBit());
					result = this.guideLogininfoMapper.updateValidBitStatus(guideLogininfo);
					if(result != 1){
						return ResultUtil.createFailureResult("00000", "删除导购时删除导购胸卡编号信息失败！");
					}else{
						String jsonData = ResultUtil.createSuccessResult(guideLogininfo);
						GuideLog guideLog = new GuideLog();
						guideLog.setGuideNo(guideinfo.getGuideNo());
						guideLog.setOperator(guideinfo.getOperator());
						guideLog.setOperatTime(new Date());
						guideLog.setDescription("删除导购时删除导购胸卡编号信息："+jsonData);
						
						try{
							this.guideLogMapper.insert(guideLog);
						}catch (Exception e) {
							this.logger.error("删除导购时删除导购胸卡编号息失败！", e);
						}
					}
				}
			}
			
//			result = this.guideinfoMapper.updateValidBitStatus(guideinfo);
//			result = this.guideinfoMapper.delValidBitStatus(guideinfo);
			result = this.guideinfoMapper.delete(guideinfo.getSid());
			if(result!=1){
				return ResultUtil.createFailureResult("00000", "删除导购基本信息失败！");
			}else{
				String jsonData = ResultUtil.createSuccessResult(guideinfo);
				GuideLog guideLog = new GuideLog();
				guideLog.setGuideNo(guideinfo.getGuideNo());
				guideLog.setOperator(guideinfo.getOperator());
				guideLog.setOperatTime(new Date());
				guideLog.setDescription("删除导购基本信息："+jsonData);
				
				try{
					this.guideLogMapper.insert(guideLog);
				}catch (Exception e) {
					this.logger.error("删除导购时添加删除导购Log信息失败！", e);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result.toString();
	}
	

}
