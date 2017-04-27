package net.shopin.supply.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.shopin.supply.domain.entity.GuideLogininfo;
import net.shopin.supply.domain.entity.Guideinfo;
import net.shopin.supply.domain.entity.GuideinfoAttach;
import net.shopin.supply.domain.vo.GuideinfoVO;
import net.shopin.supply.service.IGuideLogininfoService;
import net.shopin.supply.service.IGuideinfoService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopin.core.util.ResultUtil;

/**
 * 导购信息管理
* @ClassName: PadGuideinfoController 
* @author zhangq
* @date 2014-12-23 下午06:29:38 
*
 */
@Controller
@RequestMapping("/guideinfo")
public class GuideinfoController {
	
	@Autowired
	private IGuideinfoService guideinfoService;
	
	@Autowired
	private IGuideLogininfoService guideLogininfoService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 通过供应商门店获取导购组
	 * @param request shopId 门店id，supplyId 供应商id
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/selectGuideListByParam",method={RequestMethod.POST,RequestMethod.GET})
	public String selectGuideListByParam(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		try{
			
			String shopId = request.getParameter("shopId");
			String supplyId = request.getParameter("supplyId");
			
			if(StringUtils.isBlank(shopId)|| StringUtils.isBlank(supplyId)){
				return "{'success':'false','memo':'门店id或供应商id为空，请传入正确参数'}";
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("shopid", shopId);
			map.put("shid", supplyId);
			
			
			List<Guideinfo> guideList = this.guideinfoService.selectGuideListByParam(map);
			
			json = ResultUtil.createSuccessResult(guideList);
			
		}catch(Exception e){
			json = ResultUtil.createFailureResult(e);
		}
		
		return json;
	}
	
	/**
	 * 查询所有导购信息
	 * @param model
	 * @param request chestcardNumber 胸卡编号，guideName 导购姓名，validBit 是否有效，spell 姓名拼音，guideStatusId 导购状态
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public String getGuideinfoList(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String chestcardNumber = request.getParameter("chestcardNumber");
			String guideName = request.getParameter("guideName");
			String validBit = request.getParameter("validBit");
			String spell = request.getParameter("spell");
			String guideStatusId = request.getParameter("guideStatusId");
			String shopId = request.getParameter("shop");
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");
			String supplyId = request.getParameter("supplyId");

			if(null != guideName && !guideName.equals("")){
				map.put("name", guideName);
			}
			if(null != supplyId && !supplyId.equals("")){
				map.put("supplyId", supplyId);
			}
			if(null != spell && !spell.equals("")){
				map.put("spell", spell);
			}
			if(null != chestcardNumber && !chestcardNumber.equals("")){
				map.put("chestcardNumber", chestcardNumber);
			}
			if(null != validBit && !validBit.equals("") && !validBit.equals("-1")){
				map.put("validBit", validBit);
			}
			if(null != guideStatusId && !guideStatusId.equals("") && !guideStatusId.equals("-1")){
				map.put("guideStatusId", guideStatusId);
			}
			if(null != shopId && !shopId.equals("") && !shopId.equals("1000")){
				map.put("shopId", shopId);
			}
			
			map.put("start", Integer.parseInt(start));
			map.put("limit", Integer.parseInt(limit));
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			logger.info("开始："+df.format(new Date()));
			List<GuideinfoVO> guideinfo = this.guideinfoService.getAllGuideInfoListPage(map);
			logger.info("结束："+df.format(new Date()));
			int count = this.guideinfoService.getCountByParam(map);
			map.put("list",guideinfo);
			json = ResultUtil.createSuccessResult(map);
			json = json.substring(0, json.length()-1)+",'total':'"+count+"'}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 按门店查询导购信息
	* @Title: getAllGuideInfoListByShop 
	* @param @param model
	* @param @param request shopId 门店id，validBit 是否有效，guideStatusId 导购状态，healthCartEndtime 健康证有效截止日期
	* 						kitasEndtime 暂住证有效截止日期，
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getAllGuideInfoListByShop")
	public String getAllGuideInfoListByShop(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String shopId = request.getParameter("shopId");
			String validBit = request.getParameter("validBit");
			String guideStatusId = request.getParameter("guideStatusId");
			String healthCartEndtime = request.getParameter("healthCartEndtime");
			String kitasEndtime = request.getParameter("kitasEndtime");
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");

			if(null != shopId && !shopId.equals("") && !shopId.equals("1000")){
				map.put("shopId", shopId);
			}
			
			if(null != start && !start.equals("")){
				map.put("start", Integer.parseInt(start));
			}
			
			if(null != limit && !limit.equals("")){
				map.put("limit", Integer.parseInt(limit));
			}
			
			if(null != validBit && !validBit.equals("") && !validBit.equals("-1")){
				map.put("validBit", validBit);
			}
			
			if(null != guideStatusId && !guideStatusId.equals("") && !guideStatusId.equals("-1")){
				map.put("guideStatusId", guideStatusId);
			}
			
			if(null != healthCartEndtime && !healthCartEndtime.equals("")){
				map.put("healthCartEndtime", healthCartEndtime);
			}
			
			if(null != kitasEndtime && !kitasEndtime.equals("")){
				map.put("kitasEndtime", kitasEndtime);
			}
			
			List<GuideinfoVO> guideinfo = this.guideinfoService.getAllGuideInfoListByShopPage(map);
			int count = this.guideinfoService.getCountByParamByShop(map);
			map.put("list",guideinfo);
			json = ResultUtil.createSuccessResult(map);
			json = json.substring(0, json.length()-1)+",'total':'"+count+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 按供应商查询导购信息
	* @Title: getAllGuideInfoListBySupply 
	* @param @param model
	* @param @param request supplyId 供应商id， brandId 品牌id， guideStatusId 导购状态， validBit 是否有效
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getAllGuideInfoListBySupply")
	public String getAllGuideInfoListBySupply(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String shopId = request.getParameter("shopId");
			String supplyId = request.getParameter("supplyId");
			String brandId = request.getParameter("brandId");
			String guideStatusId = request.getParameter("guideStatusId");
			String validBit = request.getParameter("validBit");
			String name = request.getParameter("name");
			String spell = request.getParameter("spell");
			String flag = request.getParameter("flag");
			String start = request.getParameter("start");
			String limit = request.getParameter("limit");

			if(null != shopId && !shopId.equals("") && !shopId.equals("1000")){
				map.put("shopId", shopId);
			}
			if(null != supplyId && !supplyId.equals("")){
				map.put("supplyId", supplyId);
			}
			if(null != brandId && !brandId.equals("")){
				map.put("brandId", brandId);
			}
			if(null != start && !start.equals("")){
				map.put("start", Integer.parseInt(start));
			}
			if(null != limit && !limit.equals("")){
				map.put("limit", Integer.parseInt(limit));
			}
			if(null != validBit && !validBit.equals("") && !validBit.equals("-1")){
				map.put("validBit", validBit);
			}
			if(null != guideStatusId && !guideStatusId.equals("") && !guideStatusId.equals("-1")){
				map.put("guideStatusId", guideStatusId);
			}
			if(null != name && !name.equals("")){
				map.put("name", name);
			}
			if(null != spell && !spell.equals("")){
				map.put("spell", spell);
			}
			if(null != flag && !flag.equals("") && !("-1").equals(flag)){
				map.put("flag", flag);
			}
			
			List<GuideinfoVO> guideinfo = this.guideinfoService.getAllGuideInfoListBySupplyPage(map);
			int count = this.guideinfoService.getCountByParamBySupply(map);
			map.put("list",guideinfo);
			json = ResultUtil.createSuccessResult(map);
			json = json.substring(0, json.length()-1)+",'total':'"+count+"'}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 保存导购信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/save",method={RequestMethod.POST,RequestMethod.GET})
	public String save(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
        
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String sid = request.getParameter("sid");
		String username = request.getParameter("username");//系统登录用户姓名
		String name = request.getParameter("name");//导购姓名
		String spell = request.getParameter("spell");//姓名拼音
		String sex = request.getParameter("sex");//性别
		String age = request.getParameter("age");//年龄
		String stature = request.getParameter("stature");//身高
		String mobile = request.getParameter("mobile");//联系电话
		String address = request.getParameter("address");//家庭住址
		String presentAddress = request.getParameter("presentAddress");//现住址
		String email = request.getParameter("email");//邮箱
		String education = request.getParameter("education");//学历
		String guideCard = request.getParameter("guideCard");//身份证号码
		String educationCartNum = request.getParameter("educationCartNum");//学历证编号
		String kitasNum = request.getParameter("kitasNum");//暂住证编号
		
		String kitasEndTime = request.getParameter("kitasEndtime");//暂住证有效截止时间
		String healthCartNum = request.getParameter("healthCartNum");//健康证编号
		String healthCartEndTime = request.getParameter("healthCartEndtime");//健康证有效截止时间至
		String chestBit = request.getParameter("chestBit");//是否领取胸卡 0：未领取；1：临时胸卡；2：正式胸卡
		String guideBit = request.getParameter("guideBit");//是否是导购 0:不是 1:是'
		String depositBit = request.getParameter("depositBit");//是否已交押金 0：未交；1：已交
		String depositNum = request.getParameter("depositNum");//押金单据编号
		String entrytime = request.getParameter("entrytime");//办卡时间
		String leavetime = request.getParameter("leavetime");//退卡时间
		String guideStatus = request.getParameter("guideStatus");//导购状态 0：初始状态；1：在柜；2不在柜
		String familyObj = request.getParameter("familyObj");//可联络家庭成员信息
		String workObj = request.getParameter("workObj");//工作经历信息
		
		try {
			Guideinfo guideinfo = new Guideinfo();
			if (sid != null && !"".equals(sid)) {
				guideinfo.setSid(Integer.parseInt(sid));
			}
			guideinfo.setName(name);
			guideinfo.setSpell(spell);
			guideinfo.setAge(Integer.parseInt(age));
			guideinfo.setStature(stature);
			guideinfo.setSex(sex);
			guideinfo.setMobile(mobile);
			guideinfo.setAddress(address);
			guideinfo.setPresentAddress(presentAddress);
			guideinfo.setEmail(email);
			guideinfo.setEducation(education);
			guideinfo.setGuideCard(guideCard);
			guideinfo.setEducationCartNum(educationCartNum);
			guideinfo.setKitasNum(kitasNum);
			guideinfo.setKitasEndtime((kitasEndTime==null || kitasEndTime.equals(""))?null:df.parse(kitasEndTime));
			guideinfo.setHealthCartNum(healthCartNum);
			guideinfo.setHealthCartEndtime((healthCartEndTime==null|| healthCartEndTime.equals(""))?null:df.parse(healthCartEndTime));
			guideinfo.setGuideCard(guideCard);
			if(null == guideBit || "".equals(guideBit)){
				guideinfo.setGuideBit(1);
			}else{
				guideinfo.setGuideBit(Integer.parseInt(guideBit));
			}
			guideinfo.setValidBit(1);//1有效
			guideinfo.setChestBit((chestBit == null || chestBit.equals(""))?0:Integer.parseInt(chestBit));//是否领取胸卡 0：未领取；1：临时胸卡；2：正式胸卡
			guideinfo.setGuideStatus((guideStatus == null || guideStatus.equals(""))?0:Integer.parseInt(guideStatus));//导购状态 0：初始状态；1：在柜；2不在柜
			guideinfo.setOperator(username);
			guideinfo.setOperatorTime(new Date());
			guideinfo.setDepositBit((depositBit == null || depositBit.equals(""))?0:Integer.parseInt(depositBit));
			guideinfo.setDepositNum((depositNum == null || depositNum.equals(""))?null:depositNum);
			guideinfo.setEntrytime((entrytime == null || entrytime.equals(""))?null:df.parse(entrytime));
			guideinfo.setLeavetime((leavetime == null || leavetime.equals(""))?null:df.parse(leavetime));
			
			int result = this.guideinfoService.saveGuideInfo(guideinfo,familyObj,workObj);
			if(result != 1){
				json = ResultUtil.createFailureResult("00000","保存失败！");
			}else{
				json = ResultUtil.createSuccessResult("保存成功！");
			}
			
		}catch(Exception e){
			json = ResultUtil.createFailureResult("添加或更新导购信息失败",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 现有导购信息注册
	* @Title: existingGuideinfosave 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/existingGuideinfosave",method={RequestMethod.POST,RequestMethod.GET})
	public String existingGuideinfosave(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		String sid = request.getParameter("sid");
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String spell = request.getParameter("spell");
		String sex = request.getParameter("sex");
		String age = request.getParameter("age");
		String stature = request.getParameter("stature");
		String mobile = request.getParameter("mobile");
		String address = request.getParameter("address");
		String presentAddress = request.getParameter("presentAddress");
		String email = request.getParameter("email");
		String education = request.getParameter("education");
		String guideCard = request.getParameter("guideCard");
		String educationCartNum = request.getParameter("educationCartNum");
		String kitasNum = request.getParameter("kitasNum");
		String kitasEndTime = request.getParameter("kitasEndTime");
		String healthCartNum = request.getParameter("healthCartNum");
		String healthCartEndTime = request.getParameter("healthCartEndTime");
		String chestBit = request.getParameter("chestBit");
		String depositBit = request.getParameter("depositBit");
		String depositNum = request.getParameter("depositNum");
		String entrytime = request.getParameter("entrytime");
		String leavetime = request.getParameter("leavetime");
		String familyObj = request.getParameter("familyObj");
		String workObj = request.getParameter("workObj");
		String chestcardNum = request.getParameter("chestcardNum");
		String shop = request.getParameter("shopinfo");
		Integer result = 0;
		
		try {

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("chestcardNum", chestcardNum);
			GuideLogininfo guideLogininfo = this.guideLogininfoService.checkChestCardNumIsUnique(map);
			if(null != guideLogininfo){
				return ResultUtil.createFailureResult("00000", "此胸卡编号已存在，请重新输入！");
			}
			
			Guideinfo guideinfo = new Guideinfo();
			if (sid != null && !"".equals(sid)) {
				guideinfo.setSid(Integer.parseInt(sid));
			}
			guideinfo.setName(name);
			guideinfo.setSpell(spell);
			guideinfo.setAge(Integer.parseInt(age));
			guideinfo.setStature(stature);
			guideinfo.setSex(sex);
			guideinfo.setMobile(mobile);
			guideinfo.setAddress(address);
			guideinfo.setPresentAddress(presentAddress);
			guideinfo.setEmail(email);
			guideinfo.setEducation(education);
			guideinfo.setGuideCard(guideCard);
			guideinfo.setEducationCartNum(educationCartNum);
			guideinfo.setKitasNum(kitasNum);
			guideinfo.setKitasEndtime((kitasEndTime==null || kitasEndTime.equals(""))?null:df.parse(kitasEndTime));
			guideinfo.setHealthCartNum(healthCartNum);
			guideinfo.setHealthCartEndtime((healthCartEndTime==null|| healthCartEndTime.equals(""))?null:df.parse(healthCartEndTime));
			guideinfo.setGuideCard(guideCard);
			guideinfo.setGuideBit(1);
			guideinfo.setValidBit(1);//1有效
			guideinfo.setChestBit((chestBit == null || chestBit.equals(""))?0:Integer.parseInt(chestBit));//是否领取胸卡1：临时胸卡；2：正式胸卡
			guideinfo.setGuideStatus(1);//导购状态1:在柜
			guideinfo.setOperator(username);
			guideinfo.setOperatorTime(new Date());
			guideinfo.setCreatetime(new Date());
			guideinfo.setDepositBit((depositBit == null || depositBit.equals(""))?0:Integer.parseInt(depositBit));
			guideinfo.setDepositNum((depositNum == null || depositNum.equals(""))?null:depositNum);
			guideinfo.setEntrytime((entrytime == null || entrytime.equals(""))?null:df.parse(entrytime));
			guideinfo.setLeavetime((leavetime == null || leavetime.equals(""))?null:df.parse(leavetime));
			
			result = this.guideinfoService.existingGuideinfosave(guideinfo,familyObj,workObj,chestcardNum,shop);
			if(result != 1){
				json = ResultUtil.createFailureResult("00000" , "信息注册失败！");
			}else{
				json = ResultUtil.createSuccessResult("信息注册成功");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 删除导购信息
	* @Title: updateGuidelValidStatus 
	* @Description: TODO(有胸卡编号的导购只修改导购信息的有效状态，没有胸卡编号的导购永久删除) 
	* @param @param request guideNo 导购编号， username 系统登录用户姓名
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/updateGuidelValidStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateGuidelValidStatus(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		String result = "";
		
		String sid = request.getParameter("sid");
		String guideNo = request.getParameter("guideNo");
		String username = request.getParameter("username");
		String validbit = request.getParameter("validBit");
//		String userSid = request.getParameter("userSid");
		
		if(sid == null || sid.isEmpty()){
			return ResultUtil.createFailureResult("00000", "sid is null");
		}
		if(guideNo == null || guideNo.isEmpty()){
			return ResultUtil.createFailureResult("00000", "guideNo is null");
		}
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("guideNo", guideNo);
			List<GuideLogininfo> guideLogininfo = this.guideLogininfoService.selectListByParam(map);
			if(guideLogininfo.size() > 0){
				
				Guideinfo guideinfo = new Guideinfo();
				guideinfo.setSid(Integer.parseInt(sid));
				guideinfo.setValidBit(Integer.parseInt(validbit));//0无效
				guideinfo.setOperatorTime(new Date());
				guideinfo.setGuideNo(Integer.parseInt(guideNo));
				guideinfo.setOperator(username);
//				guideinfo.setOperatorSid(Integer.parseInt(userSid));
				result = this.guideinfoService.updateValidBitStatus(guideinfo);
			}else{
				result = this.guideinfoService.delGuidinfo(Integer.parseInt(sid)).toString();
			}
			
			if(!result.equals("1")){
				if(validbit.equals("0")||validbit=="0"){
					json = ResultUtil.createFailureResult("00000" , "注销失败");
				}else{
					json = ResultUtil.createFailureResult("00000" , "恢复失败");
				}
				
			}else{
				json = ResultUtil.createSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("操作失败！",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 删除导购信息
	* @Title: updateGuidelValidStatus 
	* @Description: TODO(有胸卡编号的导购只修改导购信息的有效状态，没有胸卡编号的导购永久删除) 
	* @param @param request guideNo 导购编号， username 系统登录用户姓名
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delGuidelValidStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String delGuidelValidStatus(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		String result = "";
		
		String sid = request.getParameter("sid");
		String guideNo = request.getParameter("guideNo");
		String username = request.getParameter("username");
		String validbit = request.getParameter("validBit");
//		String userSid = request.getParameter("userSid");
		
		if(sid == null || sid.isEmpty()){
			return ResultUtil.createFailureResult("00000", "sid is null");
		}
		if(guideNo == null || guideNo.isEmpty()){
			return ResultUtil.createFailureResult("00000", "guideNo is null");
		}
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("guideNo", guideNo);
			List<GuideLogininfo> guideLogininfo = this.guideLogininfoService.selectListByParam(map);
			if(guideLogininfo.size() > 0){
				
				Guideinfo guideinfo = new Guideinfo();
				guideinfo.setSid(Integer.parseInt(sid));
				guideinfo.setValidBit(Integer.parseInt(validbit));//0无效
				guideinfo.setOperatorTime(new Date());
				guideinfo.setGuideNo(Integer.parseInt(guideNo));
				guideinfo.setOperator(username);
//				guideinfo.setOperatorSid(Integer.parseInt(userSid));
				result = this.guideinfoService.delValidBitStatus(guideinfo);
			}else{
				result = this.guideinfoService.delGuidinfo(Integer.parseInt(sid)).toString();
			}
			
			if(!result.equals("1")){
					json = ResultUtil.createFailureResult("00000" , "删除失败");
			}else{
				json = ResultUtil.createSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("删除失败！",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 修改手动变价权限
	 * @Title: updateAuthorize 
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author zhangqing
	 * @date 2015-4-21
	 *//*
	@ResponseBody
	@RequestMapping(value = "/updateAuthorize", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateAuthorize(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		String sid = request.getParameter("sid");
		String guideNo = request.getParameter("guideNo");
		String username = request.getParameter("username");
		String authorize = request.getParameter("authorize");
		String endtime = request.getParameter("endtime");
		if(endtime.indexOf("T") != -1){
			endtime = endtime.replaceAll("T"," ");
		}
		
		if(sid == null || sid.isEmpty()){
			return ResultUtil.createFailureResult("00000", "sid is null");
		}
		if(guideNo == null || guideNo.isEmpty()){
			return ResultUtil.createFailureResult("00000", "guideNo is null");
		}
		
		try {
				GuideinfoVO guideinfo = this.guideinfoService.selectByGuideNo(Integer.parseInt(guideNo));
				guideinfo.setFlag(Integer.parseInt(authorize));
				guideinfo.setEndTime((endtime==null || endtime.equals(""))?null:df.parse(endtime));
				guideinfo.setOperator(username);
				guideinfo.setOperatorTime(new Date());
				
				this.guideinfoService.updateByGuideNo(guideinfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("授权失败！",e.getMessage());
		}
		return json;
	}*/
	

	/**
	 * 根据sid获取导购明细
	* @Title: getGuideinfoDetail 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/getGuideinfoDetail")
	@ResponseBody
	public String getGuideinfoDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
		String json = "";
		String sid = request.getParameter("sid");
		Guideinfo guideinfo = this.guideinfoService.selectByPrimaryKey(Integer.parseInt(sid));
		json = ResultUtil.createSuccessResult(guideinfo);
		return json;
	}
	
	/**
	 * 获取导购家庭成员信息
	* @Title: getFamilyinfoDetail 
	* @param @param model
	* @param @param request guideNo 导购编号
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getFamilyinfoDetail")
	public String getFamilyinfoDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			
			Map<String,Object> map = new HashMap<String,Object>();
			String guideNo = request.getParameter("guideNo");
			map.put("guideNo", guideNo);
			map.put("type", "1");
			List<GuideinfoAttach> guideinfoAttachList = this.guideinfoService.selectFamilyByguideNo(map);
			map.put("list",guideinfoAttachList);
			json = ResultUtil.createSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 获取导购工作经历信息
	* @Title: getWorkinfoDetail 
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/getWorkinfoDetail")
	public String getWorkinfoDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		String json = "";
		try {
			
			Map<String,Object> map = new HashMap<String,Object>();
			String guideNo = request.getParameter("guideNo");
			map.put("guideNo", guideNo);
			map.put("type", "2");
			List<GuideinfoAttach> guideinfoAttachList = this.guideinfoService.selectFamilyByguideNo(map);
			map.put("list",guideinfoAttachList);
			json = ResultUtil.createSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 保存导购附属信息(家庭成员、工作经历等信息)
	* @Title: saveGuideAttach 
	* @param @param request guideNo 导购编号， familyName 家庭成员姓名，relation 关系，mobile 联系电话，
	* 						workStarttime 工作开始时间， workEndtime 工作结束时间 ， company 公司，position 职位，leaveResult 离职原因
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/saveGuideAttach",method={RequestMethod.POST,RequestMethod.GET})
	public String saveGuideAttach(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		
		String sid = request.getParameter("sid");
		String guideNo = request.getParameter("guideNo");
		String familyName = request.getParameter("familyName");
		String relation = request.getParameter("relation");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String workStarttime = request.getParameter("workStarttime");
		String workEndtime = request.getParameter("workEndtime");
		String company = request.getParameter("company");
		String position = request.getParameter("position");
		String leaveResult = request.getParameter("leaveResult");
		
		try {
			GuideinfoAttach guideinfoAttach = new GuideinfoAttach();
			if (sid != null && !"".equals(sid)) {
				guideinfoAttach.setSid(Integer.parseInt(sid));
			}
			
			guideinfoAttach.setGuideNo(Integer.parseInt(guideNo));
			
			if(type.equals("1")){
				guideinfoAttach.setFamilyName(familyName);
				guideinfoAttach.setRelation(relation);
				guideinfoAttach.setMobile(mobile);
				guideinfoAttach.setType(1);
			}else if(type.equals("2")){
				guideinfoAttach.setWorkStarttime(df.parse(workStarttime));
				guideinfoAttach.setWorkEndtime(df.parse(workEndtime));
				guideinfoAttach.setCompany(company);
				guideinfoAttach.setType(2);
				guideinfoAttach.setPosition(position);
				guideinfoAttach.setLeaveResult(leaveResult);
			}
			
			guideinfoAttach.setCreattime(new Date());
			
			this.guideinfoService.saveGuideAttach(guideinfoAttach);
			json = ResultUtil.createSuccessResult();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 删除导购附属信息(家庭成员、工作经历等信息)
	* @Title: delGuideAttachInfo 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delGuideAttachInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public String delGuideAttachInfo(HttpServletRequest request, HttpServletResponse response) {
		String json="";
		
		String sid = request.getParameter("sid");
		try {
			this.guideinfoService.deleteGuideAttach(Long.parseLong(sid));
			json = ResultUtil.createSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			json = ResultUtil.createFailureResult("删除失败！",e.getMessage());
		}
		return json;
	}
	
	/**
	 * 导出导购基本信息报表
	* @Title: excepot 
	* @param @param request guideName 导购姓名，spell 姓名拼音，chestcardNumber 胸卡编号，validBit 是否有效，guideinfoStatus 导购状态
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/exportGuideinfoToExcel",method = {RequestMethod.GET, RequestMethod.POST})
	public String exportGuideinfoToExcel(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		
		Map<String,Object> map = new HashMap<String,Object>();
		String guideName = request.getParameter("guideName");
		String spell = request.getParameter("spell");
		String supplyId = request.getParameter("supplyId");
		String chestcardNumber = request.getParameter("chestcardNumber");
		String validBit = request.getParameter("validBit");
		String guideinfoStatus = request.getParameter("guideinfoStatus");
		String shopId = request.getParameter("shop");
		String type = request.getParameter("type");

		if(null != guideName && !guideName.equals("")){
			map.put("name", guideName);
		}
		if(null != supplyId && !supplyId.equals("")){
			map.put("supplyId", supplyId);
		}
		if(null != spell && !spell.equals("")){
			map.put("spell", spell);
		}
		if(null != chestcardNumber && !chestcardNumber.equals("")){
			map.put("chestcardNumber", chestcardNumber);
		}
		if(null != validBit && !validBit.equals("") && !validBit.equals("-1")){
			map.put("validBit", validBit);
		}
		if(null != guideinfoStatus && !guideinfoStatus.equals("") && !guideinfoStatus.equals("-1")){
			map.put("guideStatusId", guideinfoStatus);
		}
		if(null != shopId && !shopId.equals("") && !shopId.equals("1000")){
			map.put("shopId", shopId);
		}
		
		List<GuideinfoVO> guideinfoList = this.guideinfoService.getAllGuideInfoList(map);
		if(null != guideinfoList){
			String result = this.guideinfoService.exportGuideinfoToExcel(response, guideinfoList,type);
			json = ResultUtil.createSuccessResult(result);
		}else{
			json = ResultUtil.createFailureResult("00000", "导购基本信息导出Excel数据为0！");
		}
		
		return json;
	}
	
	/**
	 * 按门店查询导出Excel
	* @Title: exportGuideinfoByShopToExcel 
	* @param @param request shopId 门店id，validBit 是否有效，guideStatusId 导购状态，healthCartEndtime 健康证有效期至，kitasEndtime 暂住证有效期至
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/exportGuideinfoByShopToExcel",method = {RequestMethod.GET, RequestMethod.POST})
	public String exportGuideinfoByShopToExcel(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		
		Map<String,Object> map = new HashMap<String,Object>();
		String shopId = request.getParameter("shopId");
		String validBit = request.getParameter("validBit");
		String guideStatusId = request.getParameter("guideStatusId");
		String healthCartEndtime = request.getParameter("healthCartEndtime");
		String kitasEndtime = request.getParameter("kitasEndtime");
		String type = request.getParameter("type");

		if(null != shopId && !shopId.equals("") && !shopId.equals("1000")){
			map.put("shopId", shopId);
		}
		
		if(null != validBit && !validBit.equals("") && !validBit.equals("-1")){
			map.put("validBit", validBit);
		}
		
		if(null != guideStatusId && !guideStatusId.equals("") && !guideStatusId.equals("-1")){
			map.put("guideStatusId", guideStatusId);
		}
		
		if(null != healthCartEndtime && !healthCartEndtime.equals("") && !healthCartEndtime.equals("null")){
			map.put("healthCartEndtime", healthCartEndtime);
		}
		
		if(null != kitasEndtime && !kitasEndtime.equals("") && !kitasEndtime.equals("null")){
			map.put("kitasEndtime", kitasEndtime);
		}
		
		List<GuideinfoVO> guideinfoList = this.guideinfoService.getAllGuideInfoListByShop(map);
		if(null != guideinfoList){
			String result = this.guideinfoService.exportGuideinfoToExcel(response, guideinfoList,type);
			json = ResultUtil.createSuccessResult(result);
		}else{
			json = ResultUtil.createFailureResult("00000", "按门店统计导出Excel数据为0！");
		}
		
		return json;
	}
	
	/**
	 * 按供应商查询导出Excel
	* @Title: exportGuideinfoBySupplyToExcel 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/exportGuideinfoBySupplyToExcel",method = {RequestMethod.GET, RequestMethod.POST})
	public String exportGuideinfoBySupplyToExcel(HttpServletRequest request,HttpServletResponse response){
		
		String json = "";
		
		Map<String,Object> map = new HashMap<String,Object>();
		String shopId = request.getParameter("shopId");
		String supplyId = request.getParameter("supplyId");
		String brandId = request.getParameter("brandId");
		String guideStatusId = request.getParameter("guideStatusId");
		String validBit = request.getParameter("validBit");
		String name = request.getParameter("name");
		String spell = request.getParameter("spell");
		String flag = request.getParameter("flag");
		String type = request.getParameter("type");

		if(null != shopId && !shopId.equals("") && !"null".equals(shopId) && !shopId.equals("1000")){
			map.put("shopId", shopId);
		}
		if(null != supplyId && !supplyId.equals("") && !supplyId.equals("null")){
			map.put("supplyId", supplyId);
		}
		if(null != brandId && !brandId.equals("") && !brandId.equals("null")){
			map.put("brandId", brandId);
		}
		if(null != validBit && !validBit.equals("") && !validBit.equals("-1")){
			map.put("validBit", validBit);
		}
		if(null != guideStatusId && !guideStatusId.equals("") && !guideStatusId.equals("-1")){
			map.put("guideStatusId", guideStatusId);
		}
		if(null != name && !name.equals("") && !name.equals("1000")){
			map.put("name", name);
		}
		if(null != spell && !spell.equals("")){
			map.put("spell", spell);
		}
		if(null != flag && !flag.equals("") && !"-1".equals(flag)){
			map.put("flag", flag);
		}
		
		List<GuideinfoVO> guideinfoList = this.guideinfoService.getAllGuideInfoListBySupply(map);
		if(null != guideinfoList){
			String result = this.guideinfoService.exportGuideinfoToExcel(response, guideinfoList,type);
			json = ResultUtil.createSuccessResult(result);
		}else{
			json = ResultUtil.createFailureResult("00000", "导购基本信息需要导出数据为0！");
		}
		
		return json;
	}
}
