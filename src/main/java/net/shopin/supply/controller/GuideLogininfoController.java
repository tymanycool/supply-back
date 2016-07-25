package net.shopin.supply.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.GuideSupply;
import net.shopin.supply.domain.entity.PadBaseinfo;
import net.shopin.supply.domain.entity.PadSupply;
import net.shopin.supply.domain.entity.SystemUser;
import net.shopin.supply.domain.entity.UploadResource;
import net.shopin.supply.domain.vo.GuideinfoVO;
import net.shopin.supply.service.IGuideLogininfoService;
import net.shopin.supply.service.IGuideSupplyService;
import net.shopin.supply.service.IGuideinfoService;
import net.shopin.supply.service.IPadBaseinfoService;
import net.shopin.supply.service.IPadSupplyService;
import net.shopin.supply.service.ISystemUserService;
import net.shopin.supply.service.IUploadResourceService;
import net.shopin.supply.service.impl.SystemUserServiceImpl;
import net.shopin.supply.util.MD5Util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shopin.core.util.ResultUtil;

/**
 * 导购登录信息
* @ClassName: GuideLogininfoController 
* @author zhangq
* @date 2015-1-13 下午07:53:31 
*
 */
@Controller
@RequestMapping("/guideLogininfo")
public class GuideLogininfoController {
	
	@Autowired
	private IGuideLogininfoService guideLogininfoService;
	
	@Autowired
	private IPadBaseinfoService padBaseinfoService;
	
	@Autowired
	private IPadSupplyService padSupplyService;
	
	@Autowired
	private IGuideSupplyService guideSupplyService;
	
	@Autowired
	private IGuideinfoService guideinfoService;
	
	@Autowired
	private IUploadResourceService uploadResourceService;
	
	@Autowired
	private ISystemUserService systemUserService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 查询所有信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public String getGuideLogininfoList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			
			Map<String,Object> map = new HashMap<String,Object>();
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");
			String userName = request.getParameter("userName");
			if(null != userName && !userName.equals("")){
				map.put("userName", userName);
			}
			map.put("start", Integer.parseInt(start));
			map.put("limit", Integer.parseInt(limit));
			List<GuideLogininfo> guideLogininfo = this.guideLogininfoService.selectListByParam(map);
			
			if(guideLogininfo.size()>0){
				for(int i=0;i<guideLogininfo.size();i++){
					String chestNumStr = guideLogininfo.get(i).getChestcardShoprule() + guideLogininfo.get(i).getChestcardNum();
					guideLogininfo.get(i).setChestcardShoprule(chestNumStr);
				}
			}
			
			int count = this.guideLogininfoService.getCountByParam(map);
			map.put("list",guideLogininfo);
			map.put("total", count);
			json = ResultUtil.createSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 导购pad登录验证
	* @Title: loginToPad 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/loginToPad",method={RequestMethod.POST,RequestMethod.GET})
	public String loginToPad(HttpServletRequest request,HttpServletResponse response){
		
		logger.info("********************loginToPad*****************");
		String json = "";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String macAddress = request.getParameter("macAddress");
		
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		try{
			if(null == username || username.trim().isEmpty()){
				return ResultUtil.createFailureResult("00000", "username is null");
			}
			
			if(null == password || password.trim().isEmpty()){
				return ResultUtil.createFailureResult("00000", "password is null");
			}
			
			Map<String ,Object> map = new HashMap<String ,Object>();
			map.put("loginUsername", username);
			map.put("loginPassword", password);
			map.put("macAddress", macAddress);
			
			List list = new ArrayList();
			List resourcelist = new ArrayList();
			
			//物流对象
			if(username.trim().startsWith("Syswuliu")){  //规定：以Syswuliu开头的用户为物流人员
//				TODO
				String passwordMd5 =  (String)MD5Util.MD5(password);
				SystemUser userParam = new SystemUser();
				userParam.setUserCode(username);
				SystemUser sysUser = this.systemUserService.selectByUserCode(userParam);
				
				if(!passwordMd5.equals(sysUser.getUserPssword())){
					return ResultUtil.createFailureResult("10000", "用户名不存在或密码输入错误!");
				}else{
					UploadResource uploadResource = this.uploadResourceService.getResourcesByParam(sysUser.getShopSid());
					
					Map<String,Object> resourceMap = new HashMap<String, Object>();
					resourceMap.put("sid", uploadResource.getSid());
					resourceMap.put("type", uploadResource.getType());
					resourceMap.put("version", uploadResource.getVersion());
					resourceMap.put("url", uploadResource.getUrl());
					resourceMap.put("shopSid", uploadResource.getShopSid());
					resourceMap.put("clientVersion", uploadResource.getClientVersion());
					resourceMap.put("upgradeType", uploadResource.getUpgradeType());
					
					resourcelist.add(resourceMap);
					resultMap.put("uploadResourceDTOs", resourcelist);
					resultMap.put("shopName", sysUser.getShopName());
					resultMap.put("shopSid", sysUser.getShopSid());
					resultMap.put("userName", sysUser.getUserCode());
					resultMap.put("userPassword", password);  //用户密码：md5加密的
					resultMap.put("roleID", 5);
					resultMap.put("sid", sysUser.getSid());
					resultMap.put("lstSupply", list);
					//TODO  optRealName  这是什么信息
					resultMap.put("optUserSid", "");
					/*
					resultMap.put("optRealName", guideinfo.getOperator());
					resultMap.put("realName", guideinfo.getName());
					resultMap.put("authorize", guideinfo.getFlag());
					resultMap.put("endtime", guideinfo.getEndTime());
					resultMap.put("spell", guideinfo.getSpell());*/
					
					logger.info("********************物流*****************");
					return ResultUtil.createSuccessResult(resultMap);
				}
			}
			
			//导购+主管
			GuideLogininfo guideLogininfo = this.guideLogininfoService.selectGuideLoginByUsername(map);
			if(null == guideLogininfo){
				return ResultUtil.createFailureResult("10000", "用户名不存在或密码输入错误！");
			}
			
			
			
			if(null != guideLogininfo.getRealName() && !"".equals(guideLogininfo.getRealName())){
				
				if(guideLogininfo.getRealName().equals("超级用户")){
					
					UploadResource uploadResource = this.uploadResourceService.getResourcesByParam(guideLogininfo.getShopId());
					Map<String,Object> resourceMap = new HashMap<String, Object>();
					resourceMap.put("sid", uploadResource.getSid());
					resourceMap.put("type", uploadResource.getType());
					resourceMap.put("version", uploadResource.getVersion());
					resourceMap.put("url", uploadResource.getUrl());
					resourceMap.put("shopSid", uploadResource.getShopSid());
					resourceMap.put("clientVersion", uploadResource.getClientVersion());
					resourceMap.put("upgradeType", uploadResource.getUpgradeType());
					resourcelist.add(resourceMap);
					resultMap.put("uploadResourceDTOs", resourcelist);
					
					resultMap.put("shopName", guideLogininfo.getShopName());
					resultMap.put("shopSid", guideLogininfo.getShopId());
					resultMap.put("userName", guideLogininfo.getLoginUsername());
					resultMap.put("userPassword", guideLogininfo.getLoginPassword());
					resultMap.put("roleID", 11);    
					resultMap.put("sid", guideLogininfo.getSid());
					resultMap.put("lstSupply", list);
					logger.info("********************超级*****************");
					return ResultUtil.createSuccessResult(resultMap);
				}
			}
			
			int guideNo = guideLogininfo.getGuideNo();
			GuideinfoVO guideinfo =  this.guideinfoService.selectByGuideNo(guideNo);
			if(guideinfo.getValidBit() == 0){
				return ResultUtil.createFailureResult("00000", "用户名已失效！");
			}
			
			if(null != macAddress && !"".equals(macAddress)){
				PadBaseinfo padBaseinfo = this.padBaseinfoService.selectPadByMacaddress(map);
				if(null == padBaseinfo){
					logger.info("********************MAC地址不存在*****************");
					return ResultUtil.createFailureResult("00000", "MAC地址不存在");
				}
				int useType = padBaseinfo.getUseType();
				String padNo = padBaseinfo.getPadNo();
				map.put("padNo", padNo);
				
				if(useType == 0){//0：导购
					PadSupply padSupply = this.padSupplyService.selectPadSupplyByPadNo(map);
					
					if(null == padSupply){
						return ResultUtil.createFailureResult("00000", "此pad没有绑定供应商！");
					}
					int supplyId = padSupply.getSupplyId();
					map.put("guideNo", guideNo);
					map.put("supplyId", supplyId);
					map.put("shopId", padSupply.getShopId());
					
					List<GuideSupply> guideSupplyList = this.guideSupplyService.loginSupplyValide(map);//在同一个门店一个导购只能绑定一个供应商，返回list是因为可能在一个供应商下绑定多个品牌，导购与供应商关系表中会有多条记录
					if(null != guideSupplyList && guideSupplyList.size()>0){
//						Guideinfo guideinfo =  this.guideinfoService.selectByGuideNo(guideNo);
						resultMap.put("optRealName", guideinfo.getOperator());
						resultMap.put("optUserSid", "");
						resultMap.put("realName", guideinfo.getName());
						
						if(guideinfo.getGuideBit() == 1){//导购
							resultMap.put("roleID", 3);
						}else if(guideinfo.getGuideBit() == 0){//主管
							resultMap.put("roleID", 4);
						}
						resultMap.put("shopName", guideSupplyList.get(0).getShopName());
						resultMap.put("shopSid", guideSupplyList.get(0).getShopId());
						
						UploadResource uploadResource = this.uploadResourceService.getResourcesByParam(guideSupplyList.get(0).getShopId());
						Map<String,Object> resourceMap = new HashMap<String, Object>();
						resourceMap.put("sid", uploadResource.getSid());
						resourceMap.put("type", uploadResource.getType());
						resourceMap.put("version", uploadResource.getVersion());
						resourceMap.put("url", uploadResource.getUrl());
						resourceMap.put("shopSid", uploadResource.getShopSid());
						resourceMap.put("clientVersion", uploadResource.getClientVersion());
						resourceMap.put("upgradeType", uploadResource.getUpgradeType());
						resourcelist.add(resourceMap);
						resultMap.put("uploadResourceDTOs", resourcelist);
						
						resultMap.put("sid", guideinfo.getSid());
						resultMap.put("authorize", guideinfo.getFlag());
						resultMap.put("endtime", guideinfo.getEndTime());
						resultMap.put("spell", guideinfo.getSpell());
						resultMap.put("userName", guideLogininfo.getLoginUsername());
						resultMap.put("userPassword", guideLogininfo.getLoginPassword());
						resultMap.put("userPhone", guideinfo.getMobile());
						
						Map<String,Object> supplyMap = new HashMap<String, Object>();
						supplyMap.put("supplyId", guideSupplyList.get(0).getSupplyId());
						supplyMap.put("companyName", guideSupplyList.get(0).getSupplyName());
						list.add(supplyMap);
						resultMap.put("lstSupply", list);
						logger.info("********************导购登录成功*****************");
						return ResultUtil.createSuccessResult(resultMap);
					}else{
						logger.info("********************此导购不属于此pad所绑定供应商*****************");
						return ResultUtil.createFailureResult("00000", "此导购不属于此pad所绑定供应商！");
					}
				}else{
					
//					Guideinfo guideinfo =  this.guideinfoService.selectByGuideNo(guideNo);
					resultMap.put("optRealName", guideinfo.getOperator());
					resultMap.put("optUserSid", "");
					resultMap.put("realName", guideinfo.getName());
					
					if(guideinfo.getGuideBit() == 1){//导购
						resultMap.put("roleID", 3);
					}else if(guideinfo.getGuideBit() == 0){//主管
						resultMap.put("roleID", 4);
					}
					
					resultMap.put("sid", guideinfo.getSid());//导购在供应商平台里的sid
					resultMap.put("spell", guideinfo.getSpell());
					resultMap.put("userName", guideLogininfo.getLoginUsername());
					resultMap.put("userPassword", guideLogininfo.getLoginPassword());
					resultMap.put("userPhone", guideinfo.getMobile());
					resultMap.put("authorize", guideinfo.getFlag());
					resultMap.put("endtime", guideinfo.getEndTime());
					
					List<PadSupply> padSupplyList = this.padSupplyService.selectListByParam(map);
					
					if(null != padSupplyList && padSupplyList.size()>0){
						
						resultMap.put("shopName", padSupplyList.get(0).getShopName());
						resultMap.put("shopSid", padSupplyList.get(0).getShopId());
						
						UploadResource uploadResource = this.uploadResourceService.getResourcesByParam(padSupplyList.get(0).getShopId());
						Map<String,Object> resourceMap = new HashMap<String, Object>();
						resourceMap.put("sid", uploadResource.getSid());
						resourceMap.put("type", uploadResource.getType());
						resourceMap.put("version", uploadResource.getVersion());
						resourceMap.put("url", uploadResource.getUrl());
						resourceMap.put("shopSid", uploadResource.getShopSid());
						resourceMap.put("clientVersion", uploadResource.getClientVersion());
						resourceMap.put("upgradeType", uploadResource.getUpgradeType());
						resourcelist.add(resourceMap);
						resultMap.put("uploadResourceDTOs", resourcelist);
						
						for(int i=0;i<padSupplyList.size();i++){
							Map<String,Object> supplyMap = new HashMap<String, Object>();
							int supplySid = padSupplyList.get(i).getSupplyId();
							String supplyName = padSupplyList.get(i).getSupplyName();
							supplyMap.put("supplyId", supplySid);
							supplyMap.put("companyName", supplyName);
							list.add(supplyMap);
						}
						resultMap.put("lstSupply", list);
						logger.info("********************777777777777777*****************");
						return ResultUtil.createSuccessResult(resultMap);
					}else{
						logger.info("********************此PAD下没有绑定任何供应商*****************");
						return ResultUtil.createFailureResult("00000", "此PAD下没有绑定任何供应商！");
					}
				}
			}else{
				//mac地址为空，返回导购下绑定的所有供应商
				map.put("guideNo", guideNo);
				List<GuideSupply> guideSupplyList = this.guideSupplyService.selectListByParam(map);
				if(null != guideSupplyList){
					resultMap.put("optRealName", guideinfo.getOperator());
					resultMap.put("optUserSid", "");
					resultMap.put("realName", guideinfo.getName());
					resultMap.put("sid", guideinfo.getSid());
					resultMap.put("authorize", guideinfo.getFlag());
					resultMap.put("endtime", guideinfo.getEndTime());
					resultMap.put("spell", guideinfo.getSpell());
					resultMap.put("userName", guideLogininfo.getLoginUsername());
					resultMap.put("userPassword", guideLogininfo.getLoginPassword());
					resultMap.put("userPhone", guideinfo.getMobile());
					resultMap.put("shopName", guideSupplyList.get(0).getShopName());
					resultMap.put("shopSid", guideSupplyList.get(0).getShopId());
					
					UploadResource uploadResource = this.uploadResourceService.getResourcesByParam(guideSupplyList.get(0).getShopId());
					Map<String,Object> resourceMap = new HashMap<String, Object>();
					resourceMap.put("sid", uploadResource.getSid());
					resourceMap.put("type", uploadResource.getType());
					resourceMap.put("version", uploadResource.getVersion());
					resourceMap.put("url", uploadResource.getUrl());
					resourceMap.put("shopSid", uploadResource.getShopSid());
					resourceMap.put("clientVersion", uploadResource.getClientVersion());
					resourceMap.put("upgradeType", uploadResource.getUpgradeType());
					resourcelist.add(resourceMap);
					resultMap.put("uploadResourceDTOs", resourcelist);
					
					
					if(guideinfo.getGuideBit() == 1){//导购
						resultMap.put("roleID", 3);
						
						for(int i=0;i<guideSupplyList.size();i++){
							Map<String,Object> supplyMap = new HashMap<String, Object>();
							int supplySid = guideSupplyList.get(i).getSupplyId();
							String supplyName = guideSupplyList.get(i).getSupplyName();
							supplyMap.put("supplyId", supplySid);
							supplyMap.put("companyName", supplyName);
							list.add(supplyMap);
						}
					}else if(guideinfo.getGuideBit() == 0){//主管
						resultMap.put("roleID", 4);
					}
					
					resultMap.put("lstSupply", list);
					logger.info("********************99999999999999*****************");
					return ResultUtil.createSuccessResult(resultMap);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("success", "false");
//			return ResultUtil.createFailureResult("00000", "登录失败！");
		}
		json = gson.toJson(resultMap);
		logger.info("********************000000000000*****************"+json);
		return json;
	}
	
	/**
	 * 修改pad登录密码（不能改登录名，只能改密码）
	 * @Title: updatePasswordToPad 
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author zhangqing
	 * @date 2015-4-27
	 */
	@ResponseBody
	@RequestMapping(value="/updatePasswordToPad",method={RequestMethod.POST,RequestMethod.GET})
	public String updatePasswordToPad(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sid = request.getParameter("sid");
		
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("loginUsername", username);
			GuideLogininfo guideLogininfoObj = this.guideLogininfoService.checkIsUnique(map);
			
			//导购不能修改密码，主管可以修改自己的登录密码
			if(guideLogininfoObj.getGuideNo() != 0){
				return ResultUtil.createFailureResult("00000", "导购无权限修改！");
			}
			
			if (sid != null && !"".equals(sid)) {
				
				guideLogininfoObj.setLoginPassword(password);
				guideLogininfoObj.setSid(Integer.parseInt(sid));
				this.guideLogininfoService.saveGuideLoginInfo(guideLogininfoObj);
				
				return ResultUtil.createSuccessResult("修改成功！");
				
			}else{
				return ResultUtil.createFailureResult("10000", "sid为空！");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("success", "false");
		}
		json = gson.toJson(resultMap);
		return json;
	}
	
	
	
	/**
	 * 保存导购登录信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save",method={RequestMethod.POST,RequestMethod.GET})
	public String save(HttpServletRequest request,HttpServletResponse response){
		
		logger.info("GuideLogininfoController save  接收请求参数：sid:"+request.getParameter("sid"));
		
		String json = "";
        
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		String sid = request.getParameter("sid");
		String guideNo = request.getParameter("guideNo");
		String shop = request.getParameter("shopId");
		String shopName = request.getParameter("shopName");
		String chestcardNum = request.getParameter("chestcardNum");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		try {
			GuideLogininfo guideLogininfo = new GuideLogininfo();
			if (sid != null && !"".equals(sid)) {
				guideLogininfo.setSid(Integer.parseInt(sid));
			}
			guideLogininfo.setGuideNo(Integer.parseInt(guideNo));
			guideLogininfo.setShopId(Integer.parseInt(shop));
			guideLogininfo.setShopName(shopName);
			guideLogininfo.setLoginUsername(userName);
			guideLogininfo.setLoginPassword(password);
			
			String chestcardShoprule = "";
			if(shop.equals("1010") || shop.equals("1011")){//1010回龙观店1011草桥店
				chestcardShoprule = "S"+chestcardNum.substring(1, 4);
				chestcardNum =  chestcardNum.substring(4);
			}else{
				chestcardShoprule = "S"+chestcardNum.substring(1, 3);
				chestcardNum =  chestcardNum.substring(3);
			}
			
			guideLogininfo.setChestcardShoprule(chestcardShoprule);
			guideLogininfo.setChestcardNum(Integer.parseInt(chestcardNum));
			
			this.guideLogininfoService.saveGuideLoginInfo(guideLogininfo);
			json = ResultUtil.createSuccessResult();
			
		}catch(Exception e){
			e.printStackTrace();
			json = ResultUtil.createFailureResult("添加或更新导购登录信息失败",e.getMessage());
			logger.info("添加或更新导购登录信息返回结果失败："+json);
		}
		logger.info("添加或更新导购登录信息返回结果："+json);
		return json;
	}
	
	/**
	 * 删除导购登录信息(只修改导购登录信息的有效状态)
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateGuidelLoginValidStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateGuidelLoginValidStatus(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		logger.info("GuideLogininfoController save  接收请求参数：sid:"+request.getParameter("sid"));
		
		String sid = request.getParameter("sid");
		try {
			GuideLogininfo guideLogininfo = new GuideLogininfo();
			guideLogininfo.setSid(Integer.parseInt(sid));
			guideLogininfo.setValidBit(0);//0无效
			this.guideLogininfoService.updateValidBitStatus(guideLogininfo);
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("删除失败！",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 生成导购胸卡编号
	* @Title: saveLogininfo 
	* @param @param request shopId 门店id，shopName 门店名，guideNo 导购编号，username 系统登录用户姓名
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/saveLogininfo", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveLogininfo(HttpServletRequest request, HttpServletResponse response) {
		
		String json="";
		int result = 0;
		
		String sid = request.getParameter("sid");
		String shopId = request.getParameter("shopId");
		String shopName = request.getParameter("shopName");
		String guideNo = request.getParameter("guideNo");
		String username = request.getParameter("username");
		
		if(shopId == null || shopId.isEmpty()){
			return ResultUtil.createFailureResult("0000", "shopId is null");
		}
		
		if(shopName == null || shopName.isEmpty()){
			return ResultUtil.createFailureResult("0000", "shopName is null");
		}
		
		if(guideNo == null || guideNo.isEmpty()){
			return ResultUtil.createFailureResult("0000", "guideNo is null");
		}
		
		String chestcardShoprule = "";
		try {
			GuideLogininfo guideLogininfo = new GuideLogininfo();
			if (sid != null && !"".equals(sid)) {
				guideLogininfo.setSid(Integer.parseInt(sid));
			}
			
//			if(shopId.equals("1010") || shopId.equals("1011")){//1010回龙观店1011草桥店   2015-3-13 变更：回龙观的门店规则也是前三位 S10
			if(shopId.equals("1011")){//1011草桥店
				chestcardShoprule = "S"+shopId.substring(1, 4);
			}else if(shopId.equals("1301")){//下沙店  下沙店现有导购胸卡规则（S1301+SAP的一级品类代码+三位数字顺序号，例如：S1301H160、S1301J170）  新导购胸卡规则S13235
//				chestcardShoprule = "S"+shopId;
				chestcardShoprule = "S"+shopId.substring(0, 2);
			}else if(shopId.equals("1311")){//笕桥店 胸卡规则 S1311+序号
				chestcardShoprule = "S"+shopId.substring(0, 4);
			}else{
				chestcardShoprule = "S"+shopId.substring(2, 4);
			}
			
			guideLogininfo.setShopId(Integer.parseInt(shopId));
			guideLogininfo.setShopName(shopName);
			guideLogininfo.setGuideNo(Integer.parseInt(guideNo));
			guideLogininfo.setChestcardShoprule(chestcardShoprule);
			guideLogininfo.setCreattime(new Date());
			guideLogininfo.setValidBit(1);//是否有效 0:无效 1:有效
			guideLogininfo.setRealName("");
			
			result = this.guideLogininfoService.saveLogininfo(guideLogininfo,username);
			if(result == 1){
				json = ResultUtil.createSuccessResult();
			}
//			else if(result == 2){
//				json = ResultUtil.createFailureResult("000000", "下沙店导购请先绑定供应商再生成胸卡编号！");
//			}
			else{
				json = ResultUtil.createFailureResult("00000", "生成胸卡编号失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 导购胸卡编号修改
	* @Title: updateLogininfo 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = { "/updateLogininfo" }, method = { RequestMethod.POST })
	public String updateLogininfo(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		int result = 0;
		
		String  sid = request.getParameter("sid");
		String  shopId = request.getParameter("shopId");
		String  chestcardNumber = request.getParameter("chestcardNumber");
		String  guideNo = request.getParameter("guideNo");
		String  username = request.getParameter("username");
		
//		检查导购在某门店是否已有胸卡
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("chestcardNumber", chestcardNumber);
		List<GuideLogininfo> guideLogininfoList = this.guideLogininfoService.selectGuideChestCartList(map);
		if(guideLogininfoList.size()>0){
			json = ResultUtil.createFailureResult("100000", "此胸卡编号已被使用，请重新修改！");
			return json;
		}
		
		String chestcardShoprule = "";
		String chestcardNum = "";
		try {
			GuideLogininfo guideLogininfo = new GuideLogininfo();
			if (sid != null && !"".equals(sid)) {
				guideLogininfo.setSid(Integer.parseInt(sid));
			}
			
//			if(shopId.equals("1010") || shopId.equals("1011")){//1010回龙观店1011草桥店  2015-3-13 变更：回龙观的门店规则也是前三位 S10
			if(shopId.equals("1011")){//1011草桥店
				chestcardShoprule = "S"+shopId.substring(1, 4);
				chestcardNum = chestcardNumber.substring(4);
			}else if(shopId.equals("1301")){//下沙店  下沙店胸卡规则（S1301+SAP的一级品类代码+三位数字顺序号，例如：S1301H160、S1301J170）  S13108
				
				chestcardShoprule = "S"+shopId.substring(0, 2);
				if(chestcardNumber.indexOf("S1301") != -1){//修改在职导购胸卡编号（如：S1301H160）
					
					chestcardNum = chestcardNumber.substring(6);
				}else{//修改新生成导购胸卡编号（如：S13118）
					
					chestcardNum = chestcardNumber.substring(3);
				}
				
			}else{
				chestcardShoprule = "S"+shopId.substring(2, 4);
				chestcardNum = chestcardNumber.substring(3);
			}
			
			String password = chestcardNumber.substring(chestcardNumber.length()-4);
			
			guideLogininfo.setChestcardShoprule(chestcardShoprule);
			guideLogininfo.setChestcardNum(Integer.parseInt(chestcardNum));
			guideLogininfo.setLoginPassword(password);
			guideLogininfo.setLoginUsername(chestcardNumber);
			guideLogininfo.setChestcardNumber(chestcardNumber);
			guideLogininfo.setGuideNo(Integer.parseInt(guideNo));
			
			result = this.guideLogininfoService.saveLogininfo(guideLogininfo,username);
			if(result == 1){
				json = ResultUtil.createSuccessResult();
			}else{
				json = ResultUtil.createFailureResult("00000", "修改胸卡编号失败！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 检查导购在某门店是否已有胸卡
	* @Title: checkGuideLogininfo 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/checkGuideLogininfo")
	@ResponseBody
	public String checkGuideLogininfo(Model model, HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		String guideNo = request.getParameter("guideNo");
		String shopId = request.getParameter("shopId");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("guideNo", guideNo);
		map.put("shopId", shopId);
		GuideLogininfo guideLogininfo = null;
		List<GuideLogininfo> guideLogininfoList = this.guideLogininfoService.selectGuideChestCartList(map);
		if(guideLogininfoList.size()>0){
			for(int i = 0;i<guideLogininfoList.size();i++){
				guideLogininfo = guideLogininfoList.get(i);
			}
		}
		json = ResultUtil.createSuccessResult(guideLogininfo);
		return json;
	}
	
	/**
	 * 获取导购胸卡号
	* @Title: getGuideChestCartList 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getGuideChestCartList")
	public String getGuideChestCartList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String guideNo = request.getParameter("guideNo");
		
		String json = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("guideNo", guideNo);
			List<GuideLogininfo> guideLogininfoList = this.guideLogininfoService.selectGuideChestCartList(map);
//			if(guideLogininfoList.size()>0){
//				for(int i=0;i<guideLogininfoList.size();i++){
//					String chestNumStr = guideLogininfoList.get(i).getChestcardShoprule() + guideLogininfoList.get(i).getChestcardNum();
//					guideLogininfoList.get(i).setChestcardShoprule(chestNumStr);
//				}
//			}
			map.put("list",guideLogininfoList);
			map.put("total", guideLogininfoList.size());
			json = ResultUtil.createSuccessResult(map);
			logger.info("查询导购胸卡号返回结果为："+json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 删除导购胸卡编号
	* @Title: delGuideChestCard 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delGuideChestCard", method = {RequestMethod.GET, RequestMethod.POST})
	public String delGuideChestCard(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		
		String sid = request.getParameter("sid");
		try {
			int result = this.guideLogininfoService.delGuideChestCard(Long.parseLong(sid));
			if(result != 1){
				json = ResultUtil.createFailureResult("00000", "删除失败！");
			}else{
				json = ResultUtil.createSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
